package ru.den.shareitserver.booking.dto;

import lombok.*;
import ru.den.shareitserver.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;

    private UserShortDto booker;
    private ItemShortDto item;
}