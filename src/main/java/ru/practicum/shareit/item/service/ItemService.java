package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    ItemDto crete(ItemDto item, Long id);

    ItemDto update(ItemDto item, Long id, Long userId);

    Optional<ItemDto> findById(Long id);

    List<ItemDto> findAllItemsFromUser(Long userId);

    List<ItemDto> searchItemByText(String text);
}
