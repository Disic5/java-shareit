package ru.den.shareitserver.item.mapper;

import org.springframework.stereotype.Component;
import ru.den.shareitserver.booking.model.Booking;
import ru.den.shareitserver.item.dto.BookingShortDto;
import ru.den.shareitserver.item.dto.CommentDto;
import ru.den.shareitserver.item.dto.ItemDto;
import ru.den.shareitserver.item.dto.ItemDtoWithBookings;
import ru.den.shareitserver.item.model.Comment;
import ru.den.shareitserver.item.model.Item;
import ru.den.shareitserver.request.model.ItemRequest;
import ru.den.shareitserver.user.model.User;

import java.util.List;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest() != null ? item.getRequest().getId() : null)
                .build();
    }

    public Item toEntity(ItemDto dto, User owner, ItemRequest request) {
        return Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .owner(owner)
                .request(request)
                .build();
    }

    public ItemDtoWithBookings toDtoWithBookings(Item item, Booking lastBooking, Booking nextBooking, List<Comment> comments) {
        return ItemDtoWithBookings.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest() != null ? item.getRequest().getId() : null)
                .lastBooking(lastBooking != null ? toBookingShortDto(lastBooking) : null)
                .nextBooking(nextBooking != null ? toBookingShortDto(nextBooking) : null)
                .comments(comments.stream().map(this::toCommentDto).toList())
                .build();
    }

    private BookingShortDto toBookingShortDto(Booking booking) {
        return BookingShortDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .build();
    }

    private CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }
}