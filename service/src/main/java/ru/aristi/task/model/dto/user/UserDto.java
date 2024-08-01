package ru.aristi.task.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ru.aristi.task.model.dto.role.RoleDto;

import java.util.Set;
import java.util.UUID;

/**
 * Dto для представления информации о пользователе.
 *
 * @param id       уникальный идентификатор пользователя.
 * @param username имя пользователя.
 * @param email    адрес электронной почты пользователя.
 * @param name     имя пользователя.
 * @param lastName фамилия пользователя.
 * @param isBanned признак заблокированности пользователя.
 * @param roles    набор ролей пользователя.
 */
public record UserDto(
        UUID id,
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        boolean isBanned,
        Set<RoleDto> roles
) {
}
