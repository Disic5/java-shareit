package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ItemRequestDto {
    private Long id;
    private String request;
    private Long requestorId;
    private Instant requestDate;
}