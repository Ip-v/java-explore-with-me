package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.utils.Create;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * API администратора для работы с пользователями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {
    /**
     * Получение информации о прользователях<br>
     * <i>Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
     * либо о конкретных (учитываются указанные идентификаторы)</i>
     */
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "ids") Long[] ids,
                                  @RequestParam(name = "from", defaultValue = "10") Integer from,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получение информации о прользователях {} {} {}", ids, from, size);
        //todo implement
        return null;
    }

    /**
     * Добавление нового пользователия
     */
    @PostMapping
    public UserDto addUser(@RequestBody @Validated({Create.class}) UserDto dto) {
        log.info("Добавление нового пользователия {}", dto);
        //todo implement
        return null;
    }

    /**
     * Удаление пользователя
     */
    @DeleteMapping("/{userId}")
    public void deleteCategory(@PathVariable(name = "userId") @NotNull Long userId) {
        log.info("Удаление пользователя с ИД {}", userId);
        //todo implement
    }
}
