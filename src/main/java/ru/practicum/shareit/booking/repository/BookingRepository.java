package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByItemOwnerId(Long ownerId);

    List<Booking> findAllByBooker_IdOrderByStartDateDesc(Long userId);

    List<Booking> findByBooker_IdAndStartDateBeforeAndEndDateAfterOrderByStartDateDesc(Long userId, LocalDateTime now, LocalDateTime now1);

    List<Booking> findByBooker_IdAndEndDateBeforeOrderByStartDateDesc(Long userId, LocalDateTime now);

    List<Booking> findByBooker_IdAndStartDateAfterOrderByStartDateDesc(Long userId, LocalDateTime now);

    List<Booking> findByBooker_IdAndStatusOrderByStartDateDesc(Long userId, BookingStatus bookingState);

    boolean existsByItem_IdAndBooker_IdAndStatusAndEndDateBefore(
            Long itemId, Long bookerId, BookingStatus status, LocalDateTime end);

    Booking findFirstByItemIdAndStartDateBeforeAndStatusOrderByEndDateDesc(
            Long itemId, LocalDateTime now, BookingStatus status);

    Booking findFirstByItemIdAndStartDateAfterAndStatusOrderByStartDateAsc(
            Long itemId, LocalDateTime now, BookingStatus status);
}
