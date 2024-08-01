package ru.aristi.task.model.dto.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Dto объект для создания роли.
 *
 * @param name наименование роли.
 */
public record CreateRoleDto(
        @NotBlank
        String name
) {
}
