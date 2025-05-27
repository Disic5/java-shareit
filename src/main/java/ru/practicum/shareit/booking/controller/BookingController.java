package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateRequest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable Long bookingId,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> findAllBookingsByBooker(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String state
    ) {
        return bookingService.findAllBookingsByBookerId(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingsByOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String state
    ) {
        return bookingService.findAllBookingsByOwnerId(userId, state);
    }

    @PostMapping
    public BookingDto createBooking(@RequestBody @Valid final BookingCreateRequest request,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.createBooking(request, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateBookingStatus(@PathVariable Long bookingId,
                                          @RequestParam boolean approved,
                                          @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return bookingService.updateBookingStatus(bookingId, approved, ownerId);
    }
}