package ru.den.shareitserver.request.mapper;

import org.springframework.stereotype.Component;
import ru.den.shareitserver.item.mapper.ItemMapper;
import ru.den.shareitserver.item.model.Item;
import ru.den.shareitserver.request.dto.ItemRequestDto;
import ru.den.shareitserver.request.model.ItemRequest;
import ru.den.shareitserver.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemRequestMapper {
    private final ItemMapper itemMapper;

    public ItemRequestMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    // Преобразует сущность в DTO без items
    public ItemRequestDto toDto(ItemRequest entity) {
        return ItemRequestDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .requestorId(entity.getRequestor().getId())
                .created(entity.getCreated())
                .items(Collections.emptyList()) // по умолчанию
                .build();
    }

    // Преобразует сущность в DTO с items
    public ItemRequestDto toDtoWithItems(ItemRequest entity, List<Item> items) {
        return ItemRequestDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .requestorId(entity.getRequestor().getId())
                .created(entity.getCreated())
                .items(items.stream()
                        .map(itemMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    // Преобразует DTO в сущность
    public ItemRequest toEntity(ItemRequestDto dto, User requestor) {
        return ItemRequest.builder()
                .description(dto.getDescription())
                .requestor(requestor)
                .created(dto.getCreated() != null ? dto.getCreated() : LocalDateTime.now())
                .build();
    }
}