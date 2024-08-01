package ru.aristi.task.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Dto для создания нового пользователя.
 *
 * @param username логин пользователя.
 * @param email    электронная почта пользователя.
 * @param name     имя пользователя.
 * @param lastName фамилия пользователя.
 */
public record CreateUserDto(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        String lastName
) {
}
