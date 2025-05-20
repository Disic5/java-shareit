package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookings;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/search")
    public List<ItemDto> searchItemByName(@RequestParam String text) {
        return itemService.searchItemByText(text);
    }

    @GetMapping
    public List<ItemDto> getItemsFromUser(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.findAllItemsFromUser(ownerId);
    }

    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemDto item,
                           @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.crete(item, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Long itemId,
                                 @RequestBody CommentDto commentDto,
                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.addComment(itemId, userId, commentDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemDto item,
                              @PathVariable Long itemId,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.update(item, itemId, userId);
    }

    @GetMapping("/{id}")
    public ItemDtoWithBookings getItemById(@PathVariable Long id,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItemById(id, userId);
    }
}