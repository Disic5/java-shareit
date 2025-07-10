package ru.den.shareitserver.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.den.shareitserver.common.exception.NotFoundException;
import ru.den.shareitserver.item.model.Item;
import ru.den.shareitserver.item.repository.ItemRepository;
import ru.den.shareitserver.request.dto.ItemRequestDto;
import ru.den.shareitserver.request.mapper.ItemRequestMapper;
import ru.den.shareitserver.request.model.ItemRequest;
import ru.den.shareitserver.request.repository.ItemRequestRepository;
import ru.den.shareitserver.user.model.User;
import ru.den.shareitserver.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestMapper requestMapper;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto addItemRequest(Long userId, ItemRequestDto requestDto) {
        User user = validateUser(userId);

        if (requestDto.getDescription() == null || requestDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description is not be empty");
        }

        ItemRequest entity = requestMapper.toEntity(requestDto, user);
        itemRequestRepository.save(entity);
        return requestMapper.toDto(entity);
    }

    @Override
    public List<ItemRequestDto> getAllItemRequests(Long userId) {
        User user = validateUser(userId);
        List<ItemRequest> itemRequestByRequestorIdOrderByCreated = itemRequestRepository.findItemRequestByRequestor_IdOrderByCreated(user.getId());
        return itemRequestByRequestorIdOrderByCreated
                .stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getItemRequest(Long requestId, Long userId) {
        validateUser(userId);

        ItemRequest request = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found with id = " + requestId));

        List<Item> items = itemRepository.findAllByRequestId(requestId);

        return requestMapper.toDtoWithItems(request, items);
    }

    private User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + userId));
    }

}
