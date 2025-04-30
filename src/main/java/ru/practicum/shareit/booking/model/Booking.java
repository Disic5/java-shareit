package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.CommonIdGenerator;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class Booking implements CommonIdGenerator {
    private Long id;
    private Item item;
    private User booker;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BookingStatus status;
}