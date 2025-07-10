package ru.den.shareitgateway.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.den.shareitgateway.item.dto.CommentDto;
import ru.den.shareitgateway.item.dto.ItemDto;

import static ru.den.shareitgateway.util.StringUtil.HEADER_USER_ID;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping("/search")
    public ResponseEntity<Object> searchItemByName(@RequestHeader(HEADER_USER_ID) long userId,
                                                   @RequestParam String text) {
        log.info("Search item by text '{}', userId {}", text, userId);
        return itemClient.searchItemByName(userId, text);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsFromUser(@RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Get items for user {}", ownerId);
        return itemClient.getItemsFromUser(ownerId);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Valid ItemDto item,
                                          @RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Add item {}, ownerId={}", item, ownerId);
        return itemClient.addItem(ownerId, item);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable Long itemId,
                                             @RequestBody @Valid CommentDto commentDto,
                                             @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Add comment to item {}, userId={}, text={}", itemId, userId, commentDto.getText());
        return itemClient.addComment(userId, itemId, commentDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemDto item,
                                             @PathVariable Long itemId,
                                             @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Update item {}, userId={}, fields={}", itemId, userId, item);
        return itemClient.updateItem(userId, itemId, item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable Long id,
                                              @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Get item {}, userId={}", id, userId);
        return itemClient.getItemById(userId, id);
    }
}