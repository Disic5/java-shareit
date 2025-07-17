package ru.den.shareitserver.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.den.shareitserver.booking.dto.BookingCreateRequest;
import ru.den.shareitserver.booking.dto.BookingDto;
import ru.den.shareitserver.booking.service.BookingService;

import java.util.List;

import static ru.den.shareitserver.util.StringUtil.HEADER_USER_ID;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;


    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable Long bookingId,
                                     @RequestHeader(HEADER_USER_ID) Long userId) {
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> findAllBookingsByBooker(
            @RequestHeader(HEADER_USER_ID) Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String state
    ) {
        return bookingService.findAllBookingsByBookerId(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingsByOwner(
            @RequestHeader(HEADER_USER_ID) Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String state
    ) {
        return bookingService.findAllBookingsByOwnerId(userId, state);
    }

    @PostMapping
    public BookingDto createBooking(@RequestBody final BookingCreateRequest request,
                                    @RequestHeader(HEADER_USER_ID) Long userId) {
        return bookingService.createBooking(request, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateBookingStatus(@PathVariable Long bookingId,
                                          @RequestParam boolean approved,
                                          @RequestHeader(HEADER_USER_ID) Long userId) {
        return bookingService.updateBookingStatus(bookingId, approved, userId);
    }
}