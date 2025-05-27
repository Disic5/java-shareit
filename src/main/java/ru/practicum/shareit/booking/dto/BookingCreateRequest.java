package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreateRequest {
    private Long itemId;

    @FutureOrPresent(message = "Start date must be now or in the future")
    private LocalDateTime start;

    @Future(message = "End date must be in the future")
    private LocalDateTime end;
}