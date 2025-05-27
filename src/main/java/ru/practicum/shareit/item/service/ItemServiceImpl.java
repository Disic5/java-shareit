package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.exception.ForbiddenException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookings;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemMapper itemMapper;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
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
        itemRepository.save(entity);
        return itemMapper.toDto(entity);
    }

    @Transactional
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

        Item updated = itemRepository.save(item);
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

    @Override
    @Transactional
    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean hasBooking = bookingRepository.existsByItem_IdAndBooker_IdAndStatusAndEndDateBefore(
                itemId, userId, BookingStatus.APPROVED, LocalDateTime.now());

        if (!hasBooking) {
            throw new ValidationException("User did not book this item or booking not yet completed.");
        }

        Comment comment = commentMapper.toEntity(commentDto, item, user);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    public ItemDtoWithBookings getItemById(Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        List<Comment> comments = commentRepository.findAllByItemId(itemId);

        Booking last = null;
        Booking next = null;

        if (item.getOwner().getId().equals(userId)) {
            last = bookingRepository
                    .findFirstByItemIdAndStartDateBeforeAndStatusOrderByEndDateDesc(itemId, LocalDateTime.now(), BookingStatus.APPROVED);

            next = bookingRepository.findFirstByItemIdAndStartDateAfterAndStatusOrderByStartDateAsc(itemId, LocalDateTime.now(), BookingStatus.APPROVED);
        }

        return itemMapper.toDtoWithBookings(item, last, next, comments);
    }
}