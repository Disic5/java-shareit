package ru.den.shareitserver.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.den.shareitserver.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ItemRequestDto {
    private Long id;
    private String description;
    private Long requestorId;
    private LocalDateTime created;
    private List<ItemDto> items;
}