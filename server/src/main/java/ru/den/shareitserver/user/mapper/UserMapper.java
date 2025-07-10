package ru.den.shareitserver.user.mapper;

import org.springframework.stereotype.Component;
import ru.den.shareitserver.user.dto.UserDto;
import ru.den.shareitserver.user.model.User;


import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public List<UserDto> toListUserDto(List<User> all) {
        List<UserDto> result = new ArrayList<>();
        for (User user : all) {
            result.add(toUserDto(user));
        }
        return result;
    }
}