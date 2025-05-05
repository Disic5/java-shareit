package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.ForbiddenException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto crete(ItemDto dto, Long ownerId) {
        User owner = userRepository
                .findById(ownerId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ItemRequest request = null;
        if (dto.getRequestId() != null) {
            request = itemRequestRepository
                    .findById(dto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Request not found"));
        }
        Item entity = itemMapper.toEntity(dto, owner, request);
        itemRepository.create(entity);
        return itemMapper.toDto(entity);
    }

    @Override
    public ItemDto update(ItemDto dto, Long id, Long userId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        if (!item.getOwner().getId().equals(userId)) {
            throw new ForbiddenException("Only the owner can update the item");
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            item.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            item.setDescription(dto.getDescription());
        }

        if (dto.getAvailable() != null) {
            item.setAvailable(dto.getAvailable());
        }

        Item updated = itemRepository.update(item);
        return itemMapper.toDto(updated);
    }

    @Override
    public ItemDto findById(Long id) {
        return itemRepository
                .findById(id)
                .map(itemMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    @Override
    public List<ItemDto> findAllItemsFromUser(Long ownerId) {
        User user = userRepository
                .findById(ownerId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return itemRepository.findAll()
                .stream()
                .filter(item -> item.getOwner().getId().equals(user.getId()))
                .map(itemMapper::toDto).toList();
    }

    @Override
    public List<ItemDto> searchItemByText(String text) {

        return itemRepository.findAll().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().equalsIgnoreCase(text.toLowerCase())
                        || item.getDescription().equalsIgnoreCase(text.toLowerCase()))
                .map(itemMapper::toDto)
                .toList();
    }
}