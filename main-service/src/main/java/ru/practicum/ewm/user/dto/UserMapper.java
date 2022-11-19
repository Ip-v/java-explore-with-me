package ru.practicum.ewm.user.dto;

import ru.practicum.ewm.user.model.User;

/**
 * User mapper class
 */
public class UserMapper {
    /**
     * UserDto -> User
     */
    public static User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    /**
     * UserDto -> User
     */
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
