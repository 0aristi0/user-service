package ru.aristi.task.model.dto.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Dto объект для передачи информации о роли.
 *
 * @param id   уникальный идентификатор роли.
 * @param name наименование роли.
 */
public record RoleDto(
        Short id,
        @NotBlank
        String name
) {
}
