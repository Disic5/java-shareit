package ru.den.shareitserver.item.mapper;

import org.springframework.stereotype.Component;
import ru.den.shareitserver.item.dto.CommentDto;
import ru.den.shareitserver.item.model.Comment;
import ru.den.shareitserver.item.model.Item;
import ru.den.shareitserver.user.model.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public Comment toEntity(CommentDto dto, Item item, User user) {
        return Comment.builder()
                .text(dto.getText())
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();
    }
}