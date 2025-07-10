package ru.den.shareitserver.booking.service;

import ru.den.shareitserver.booking.dto.BookingCreateRequest;
import ru.den.shareitserver.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingCreateRequest request, Long id);

    BookingDto getBookingById(Long id, Long userId);

    List<BookingDto> findAllBookingsByBookerId(Long userId, String state);

    List<BookingDto> findAllBookingsByOwnerId(Long id, String state);

    BookingDto updateBookingStatus(Long bookingId, boolean approved, Long userId);

}
