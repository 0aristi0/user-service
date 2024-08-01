package ru.aristi.task.model.dto.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Dto объект для обновления роли.
 *
 * @param name новое наименование роли.
 */
public record UpdateRoleDto(
        @NotBlank
        String name
) {
}
