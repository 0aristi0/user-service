package ru.aristi.task.model.dto.user;

import java.util.Set;

/**
 * Dto для изменения ролей пользователя.
 *
 * @param roles множество ролей, которые будут установлены пользователю.
 */
public record ChangeUserRoleDto(
        Set<String> roles
) {
}
