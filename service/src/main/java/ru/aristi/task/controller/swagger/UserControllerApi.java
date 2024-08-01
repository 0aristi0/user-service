package ru.aristi.task.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aristi.task.model.dto.user.ChangeUserRoleDto;
import ru.aristi.task.model.dto.user.CreateUserDto;
import ru.aristi.task.model.dto.user.UpdateUserDto;
import ru.aristi.task.model.dto.user.UserDto;

import java.util.UUID;

/**
 * Swagger описание контроллера для работы с пользователями.
 */
@Tag(name = "Users", description = "Пользователи")
public interface UserControllerApi {

    @Operation(summary = "Создает пользователя")
    UserDto createUser(@RequestBody @Valid CreateUserDto dto);

    @Operation(summary = "Возвращает пользователя")
    UserDto getUser(@PathVariable UUID id);

    @Operation(summary = "Обновляет пользователя")
    UserDto updateUser(@PathVariable UUID id, @RequestBody @Valid UpdateUserDto dto);

    @Operation(summary = "Обновляет роли пользователя")
    UserDto updateUserRoles(@PathVariable UUID id, @RequestBody ChangeUserRoleDto dto);

    @Operation(summary = "Блокирует пользователя")
    void banUser(@PathVariable UUID id);

    @Operation(summary = "Разблокирует пользователя")
    void unbanUser(@PathVariable UUID id);

    @Operation(summary = "Удаляет пользователя")
    void deleteUser(@PathVariable UUID id);
}
