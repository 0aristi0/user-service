package ru.aristi.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.aristi.task.model.business.user.CreateUserModel;
import ru.aristi.task.model.business.user.UpdateUserModel;
import ru.aristi.task.model.business.user.UserModel;
import ru.aristi.task.model.dto.user.CreateUserDto;
import ru.aristi.task.model.dto.user.UpdateUserDto;
import ru.aristi.task.model.dto.user.UserDto;
import ru.aristi.task.model.entity.User;

/**
 * Маппер для работы с сущностью {@link User}.
 */
@Mapper(uses = RoleMapper.class)
public interface UserMapper {

    User toEntity(CreateUserModel model);

    @Mapping(target = "isBanned", expression = "java(entity.isBanned())")
    UserModel toModel(User entity);

    UpdateUserModel toModel(UpdateUserDto dto);

    CreateUserModel toModel(CreateUserDto dto);

    UserDto toDto(UserModel model);
}

