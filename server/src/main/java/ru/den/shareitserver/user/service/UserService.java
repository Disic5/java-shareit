package ru.den.shareitserver.user.service;


import ru.den.shareitserver.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto user);

    UserDto updateUser(UserDto user, Long id);

    void deleteUser(Long id);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

}