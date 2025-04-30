package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class BookingMapper {
    public BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .bookingId(booking.getId())
                .itemId(booking.getItem().getId())
                .bookerId(booking.getBooker().getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .status(booking.getStatus())
                .build();
    }

    public Booking toEntity(BookingDto bookingDto, Item item, User user) {
        return Booking.builder()
                .id(bookingDto.getBookingId())
                .item(item)
                .booker(user)
                .startDate(bookingDto.getStartDate())
                .endDate(bookingDto.getEndDate())
                .status(bookingDto.getStatus())
                .build();
    }
}