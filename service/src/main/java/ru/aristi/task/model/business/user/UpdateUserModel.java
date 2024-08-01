package ru.aristi.task.model.business.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Модель для обновления информации о пользователе.
 *
 * @param name     новое имя пользователя.
 * @param lastName новая фамилия пользователя.
 * @param email    новый адрес электронной почты пользователя.
 */
public record UpdateUserModel(
        @NotBlank
        String name,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email
) {
}
