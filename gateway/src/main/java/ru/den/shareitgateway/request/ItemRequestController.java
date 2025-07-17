package ru.den.shareitgateway.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.den.shareitgateway.request.dto.ItemRequestDto;

import static ru.den.shareitgateway.util.StringUtil.HEADER_USER_ID;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    /**
     * Добавление запроса вещи
     */
    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader(HEADER_USER_ID) Long userId,
                                                 @Valid @RequestBody ItemRequestDto requestDto) {
        return itemRequestClient.addItemRequest(userId, requestDto);
    }

    /**
     * Получение всех своих запросов
     */
    @GetMapping
    public ResponseEntity<Object> getAllItemRequests(@RequestHeader(HEADER_USER_ID) Long userId) {
        return itemRequestClient.getAllItemRequests(userId);
    }

    /**
     * Получение одного запроса по ID
     */
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@PathVariable Long requestId,
                                                     @RequestHeader(HEADER_USER_ID) Long userId) {
        return itemRequestClient.getItemRequestById(userId, requestId);
    }
}
