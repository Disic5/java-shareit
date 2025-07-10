package ru.den.shareitserver.booking.mapper;

import org.springframework.stereotype.Component;
import ru.den.shareitserver.booking.dto.BookingCreateRequest;
import ru.den.shareitserver.booking.dto.BookingDto;
import ru.den.shareitserver.booking.dto.ItemShortDto;
import ru.den.shareitserver.booking.dto.UserShortDto;
import ru.den.shareitserver.booking.model.Booking;
import ru.den.shareitserver.item.model.Item;
import ru.den.shareitserver.user.model.User;

@Component
public class BookingMapper {

    public BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStartDate())
                .end(booking.getEndDate())
                .status(booking.getStatus())
                .booker(UserShortDto.builder()
                        .id(booking.getBooker().getId())
                        .build())
                .item(ItemShortDto.builder()
                        .id(booking.getItem().getId())
                        .name(booking.getItem().getName())
                        .build())
                .build();
    }

    public Booking toEntity(BookingCreateRequest request, Item item, User booker) {
        return Booking.builder()
                .item(item)
                .booker(booker)
                .startDate(request.getStart())
                .endDate(request.getEnd())
                .build();
    }
}