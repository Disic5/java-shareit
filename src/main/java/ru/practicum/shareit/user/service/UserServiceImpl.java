package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't be null");
        }
        validationEmail(dto, dto.getId());
        User user = userMapper.toUser(dto);
        User userCreated = userRepository.save(user);
        return userMapper.toUserDto(userCreated);
    }

    @Override
    public UserDto updateUser(UserDto dto, Long id) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't be null");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        validationEmailNotBeNull(dto, id);
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        User updated = userRepository.save(user);
        return userMapper.toUserDto(updated);
    }

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

    private void validationEmail(UserDto user, Long currentUserId) {
        if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email can't be null");
        }
        boolean emailExist = userRepository.findAll().stream()
                .anyMatch(u -> user.getEmail().equals(u.getEmail()) && !u.getId().equals(currentUserId));
        if (emailExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }

    private void validationEmailNotBeNull(UserDto dto, Long currentUserId) {
        String email = dto.getEmail();
        if (email == null) {
            return;
        }
        boolean emailExist = userRepository.findAll().stream()
                .anyMatch(u -> dto.getEmail().equals(u.getEmail()) && !u.getId().equals(currentUserId));
        if (emailExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }
}