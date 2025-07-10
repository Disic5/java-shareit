package ru.den.shareitserver.item.service;

import ru.den.shareitserver.item.dto.CommentDto;
import ru.den.shareitserver.item.dto.ItemDto;
import ru.den.shareitserver.item.dto.ItemDtoWithBookings;

import java.util.List;

public interface ItemService {
    ItemDto crete(ItemDto item, Long id);

    ItemDto update(ItemDto item, Long id, Long userId);

    ItemDto findById(Long id);

    List<ItemDto> findAllItemsFromUser(Long userId);

    List<ItemDto> searchItemByText(String text);

    CommentDto addComment(Long itemId, Long userId, CommentDto commentDto);

    ItemDtoWithBookings getItemById(Long itemId, Long userId);
}
