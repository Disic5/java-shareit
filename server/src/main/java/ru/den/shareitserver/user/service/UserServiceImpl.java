package ru.den.shareitserver.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.den.shareitserver.user.dto.UserDto;
import ru.den.shareitserver.user.mapper.UserMapper;
import ru.den.shareitserver.user.model.User;
import ru.den.shareitserver.user.repository.UserRepository;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDto addUser(UserDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        User user = userMapper.toUser(dto);
        User userCreated = userRepository.save(user);
        return userMapper.toUserDto(userCreated);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto dto, Long id) {
        if (dto == null) {
            throw new IllegalArgumentException("User can't be null");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        User updated = userRepository.save(user);
        return userMapper.toUserDto(updated);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserDto)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toListUserDto(userRepository.findAll());
    }
}