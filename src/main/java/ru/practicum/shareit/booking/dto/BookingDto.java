package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDate;

@Data
@Builder
public class BookingDto {
    private Long bookingId;
    private Long itemId;
    private Long bookerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;
}