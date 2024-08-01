package ru.aristi.task.model.business.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Модель для создания роли.
 *
 * @param name наименование роли.
 */
public record CreateRoleModel(
        @NotBlank
        String name
) {
}
