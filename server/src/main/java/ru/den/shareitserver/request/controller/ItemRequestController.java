package ru.den.shareitserver.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.den.shareitserver.request.dto.ItemRequestDto;
import ru.den.shareitserver.request.service.ItemRequestService;

import java.util.List;

import static ru.den.shareitserver.item.controller.ItemController.X_SHARER_USER_ID;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService requestService;

    @PostMapping
    public ItemRequestDto addItemRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                         @RequestBody ItemRequestDto requestDto) {
        return requestService.addItemRequest(userId, requestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getAllItemRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return requestService.getAllItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getAllItemRequestById(@PathVariable Long requestId,
                                                @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return requestService.getItemRequest(requestId, userId);
    }

}
