package ru.aristi.task.mapper;

import org.mapstruct.Mapper;
import ru.aristi.task.model.business.role.CreateRoleModel;
import ru.aristi.task.model.business.role.RoleModel;
import ru.aristi.task.model.business.role.UpdateRoleModel;
import ru.aristi.task.model.dto.role.CreateRoleDto;
import ru.aristi.task.model.dto.role.RoleDto;
import ru.aristi.task.model.dto.role.UpdateRoleDto;
import ru.aristi.task.model.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * Маппер для работы с сущностью {@link Role}.
 */
@Mapper
public interface RoleMapper {

    Role toEntity(CreateRoleModel model);

    CreateRoleModel toModel(CreateRoleDto dto);

    UpdateRoleModel toModel(UpdateRoleDto dto);

    RoleModel toModel(Role role);

    Set<RoleModel> toModels(Set<Role> entities);

    List<RoleModel> toModels(List<Role> entities);

    RoleDto toDto(RoleModel model);

    List<RoleDto> toDtos(List<RoleModel> models);
}
