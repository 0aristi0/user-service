package ru.aristi.task.model.business.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Модель для передачи информации о роли.
 *
 * @param id   уникальный идентификатор роли.
 * @param name наименование роли.
 */
public record RoleModel(
        Short id,
        @NotBlank
        String name
) {
}
