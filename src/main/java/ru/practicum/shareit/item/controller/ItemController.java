package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable Long id) {
        return itemService.findById(id);
    }

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

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemDto item,
                              @PathVariable Long itemId,
                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.update(item, itemId, userId);
    }
}