package ru.aristi.task.model.business.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.aristi.task.model.business.role.RoleModel;

import java.util.Set;
import java.util.UUID;

/**
 * Модель для представления информации о пользователе.
 *
 * @param id       уникальный идентификатор пользователя.
 * @param username имя пользователя.
 * @param email    адрес электронной почты пользователя.
 * @param name     имя пользователя.
 * @param lastName фамилия пользователя.
 * @param isBanned признак заблокированности пользователя.
 * @param roles    набор ролей пользователя.
 */
public record UserModel(
        @NotNull
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
        Set<RoleModel> roles
) {

}
