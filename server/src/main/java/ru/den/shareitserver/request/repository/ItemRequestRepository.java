package ru.den.shareitserver.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.den.shareitserver.request.model.ItemRequest;

import java.util.List;


public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findItemRequestByRequestor_IdOrderByCreated(Long requestorId);
}
