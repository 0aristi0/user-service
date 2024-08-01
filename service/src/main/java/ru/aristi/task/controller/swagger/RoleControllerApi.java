package ru.aristi.task.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aristi.task.model.dto.role.CreateRoleDto;
import ru.aristi.task.model.dto.role.RoleDto;
import ru.aristi.task.model.dto.role.UpdateRoleDto;

import java.util.List;

/**
 * Swagger описание контроллера для работы с ролями.
 */
@Tag(name = "Roles", description = "Роли")
public interface RoleControllerApi {

    @Operation(summary = "Создает роль")
    RoleDto createRole(@RequestBody @Valid CreateRoleDto dto);

    @Operation(summary = "Возвращает роль")
    RoleDto getRole(@PathVariable String name);

    @Operation(summary = "Возвращает все роли")
    List<RoleDto> getAllRoles();

    @Operation(summary = "Обновляет роль")
    RoleDto updateRole(@PathVariable String name, @RequestBody @Valid UpdateRoleDto dto);

    @Operation(summary = "Удаляет роль")
    void deleteRole(@PathVariable String name);
}