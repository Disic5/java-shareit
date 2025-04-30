package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto addUser(UserDto user);

    UserDto updateUser(UserDto user, Long id);

    boolean deleteUser(Long id);

    Optional<UserDto> getUserById(Long id);

    List<UserDto> getAllUsers();

}