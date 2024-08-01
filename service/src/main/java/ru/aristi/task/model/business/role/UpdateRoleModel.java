package ru.aristi.task.model.business.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Модель для обновления роли.
 *
 * @param name новое наименование роли.
 */
public record UpdateRoleModel(
        @NotBlank
        String name
) {
}
