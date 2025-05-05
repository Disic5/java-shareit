package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long bookingId;
    private Long itemId;
    private Long bookerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus status;
}