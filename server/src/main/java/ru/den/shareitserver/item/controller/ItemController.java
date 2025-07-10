package ru.den.shareitserver.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.den.shareitserver.item.dto.CommentDto;
import ru.den.shareitserver.item.dto.ItemDto;
import ru.den.shareitserver.item.dto.ItemDtoWithBookings;
import ru.den.shareitserver.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping("/search")
    public List<ItemDto> searchItemByName(@RequestParam String text) {
        return itemService.searchItemByText(text);
    }

    @GetMapping
    public List<ItemDto> getItemsFromUser(@RequestHeader(X_SHARER_USER_ID) Long ownerId) {
        return itemService.findAllItemsFromUser(ownerId);
    }

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto item,
                           @RequestHeader(X_SHARER_USER_ID) Long ownerId) {
        return itemService.crete(item, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Long itemId,
                                 @RequestBody CommentDto commentDto,
                                 @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.addComment(itemId, userId, commentDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemDto item,
                              @PathVariable Long itemId,
                              @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.update(item, itemId, userId);
    }

    @GetMapping("/{id}")
    public ItemDtoWithBookings getItemById(@PathVariable Long id,
                                           @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.getItemById(id, userId);
    }
}