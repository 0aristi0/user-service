package ru.aristi.task.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Dto для обновления информации о пользователе.
 *
 * @param name     новое имя пользователя.
 * @param lastName новая фамилия пользователя.
 * @param email    новый адрес электронной почты пользователя.
 */
public record UpdateUserDto(
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email
) {
}
