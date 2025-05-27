package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.dto.BookingCreateRequest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.model.BookingStatus.WAITING;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingDto createBooking(BookingCreateRequest request, Long userId) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NotFoundException("Item not found"));

        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!Boolean.TRUE.equals(item.getAvailable())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item not available");
        }

        if (request.getStart() == null || request.getEnd() == null || !request.getStart().isBefore(request.getEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid dates");
        }

        if (request.getStart().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date in the past");
        }

        Booking booking = bookingMapper.toEntity(request, item, booker);
        booking.setStatus(WAITING);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        Long bookerId = booking.getBooker().getId();
        Long ownerId = booking.getItem().getOwner().getId();

        if (!bookerId.equals(userId) && !ownerId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not have access to this booking");
        }

        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> findAllBookingsByBookerId(Long userId, String stateRaw) {
        BookingState state;
        try {
            state = BookingState.valueOf(stateRaw.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown state: " + stateRaw);
        }

        List<Booking> bookings;

        LocalDateTime now = LocalDateTime.now();

        switch (state) {
            case ALL -> bookings = bookingRepository.findAllByBooker_IdOrderByStartDateDesc(userId);
            case CURRENT -> bookings = bookingRepository.findByBooker_IdAndStartDateBeforeAndEndDateAfterOrderByStartDateDesc(userId, now, now);
            case PAST -> bookings = bookingRepository.findByBooker_IdAndEndDateBeforeOrderByStartDateDesc(userId, now);
            case FUTURE -> bookings = bookingRepository.findByBooker_IdAndStartDateAfterOrderByStartDateDesc(userId, now);
            case WAITING -> bookings = bookingRepository.findByBooker_IdAndStatusOrderByStartDateDesc(userId, BookingStatus.WAITING);
            case REJECTED -> bookings = bookingRepository.findByBooker_IdAndStatusOrderByStartDateDesc(userId, BookingStatus.REJECTED);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported state: " + state);
        }

        return bookings.stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public List<BookingDto> findAllBookingsByOwnerId(Long id, String state) {
        boolean hasItems = itemRepository.existsByOwnerId(id);
        if (!hasItems) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not own any items");
        }
        return bookingRepository.findAllByItemOwnerId(id)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookingDto updateBookingStatus(Long bookingId, boolean approved, Long ownerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only owner can approve/reject bookings");
        }

        if (booking.getStatus() != BookingStatus.WAITING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking already processed");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

}
