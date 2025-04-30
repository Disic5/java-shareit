package ru.practicum.shareit.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemRequestMapper {
    public ItemRequestDto toDto(ItemRequest item) {
        return ItemRequestDto.builder()
                .id(item.getId())
                .request(item.getDescription())
                .requestorId(item.getRequestor().getId())
                .requestDate(item.getCreated())
                .build();
    }

    public ItemRequest toEntity(ItemRequestDto dto, User requestor) {
        return ItemRequest.builder()
                .id(dto.getId())
                .description(dto.getRequest())
                .requestor(requestor)
                .created(dto.getRequestDate())
                .build();
    }
}