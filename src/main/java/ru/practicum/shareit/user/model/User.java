package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.CommonIdGenerator;

@Data
@Builder
public class User implements CommonIdGenerator {
    private Long id;
    private String name;
    @Email
    private String email;
}