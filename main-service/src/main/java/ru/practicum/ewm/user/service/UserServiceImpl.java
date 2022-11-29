package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис пользователей
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAll(Long[] ids, Integer from, Integer size) {
        List<UserDto> users = new ArrayList<>();
        if (ids != null) {
            repository.findAllById(List.of(ids)).forEach(u -> users.add(UserMapper.toUserDto(u)));
        } else {
            int page = from / size;
            Pageable pageRequest = PageRequest.of(page, size);
            repository.findAll(pageRequest).forEach(u -> users.add(UserMapper.toUserDto(u)));
        }
        return users;
    }

    @Override
    @Transactional
    public UserDto add(UserDto dto) {
        final User user = UserMapper.toUser(dto);
        final User save = repository.save(user);
        return UserMapper.toUserDto(save);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        repository.deleteById(userId);
    }
}
