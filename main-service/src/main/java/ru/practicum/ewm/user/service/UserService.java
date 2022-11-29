package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

/**
 * Сервис пользователей
 */
public interface UserService {
    /**
     * Получение информации о прользователях<br>
     * <i>Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
     * либо о конкретных (учитываются указанные идентификаторы)</i>
     */
    List<UserDto> getAll(Long[] ids, Integer from, Integer size);

    /**
     * Добавление нового пользователия
     */
    UserDto add(UserDto dto);

    /**
     * Удаление пользователя
     */
    void delete(Long userId);
}
