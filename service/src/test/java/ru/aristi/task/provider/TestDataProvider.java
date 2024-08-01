package ru.aristi.task.provider;


import org.instancio.Instancio;
import org.instancio.Select;
import ru.aristi.task.model.business.role.CreateRoleModel;
import ru.aristi.task.model.business.role.UpdateRoleModel;
import ru.aristi.task.model.business.user.CreateUserModel;
import ru.aristi.task.model.business.user.UpdateUserModel;
import ru.aristi.task.model.dto.role.CreateRoleDto;
import ru.aristi.task.model.dto.role.UpdateRoleDto;
import ru.aristi.task.model.dto.user.ChangeUserRoleDto;
import ru.aristi.task.model.dto.user.CreateUserDto;
import ru.aristi.task.model.dto.user.UpdateUserDto;

import java.util.Set;

public final class TestDataProvider {

    public static CreateRoleDto createRoleDto() {
        return Instancio.of(CreateRoleDto.class)
                .create();
    }

    public static UpdateRoleDto updateRoleDto() {
        return Instancio.of(UpdateRoleDto.class)
                .create();
    }

    public static CreateUserDto createUserDto() {
        return Instancio.of(CreateUserDto.class)
                .create();
    }

    public static UpdateUserDto updateUserDto() {
        return Instancio.of(UpdateUserDto.class)
                .create();
    }

    public static UpdateUserModel updateUserModel() {
        return Instancio.of(UpdateUserModel.class)
                .create();
    }

    public static CreateUserModel createUserModel() {
        return Instancio.of(CreateUserModel.class)
                .create();
    }

    public static UpdateRoleModel updateRoleModel() {
        return Instancio.of(UpdateRoleModel.class)
                .create();
    }

    public static CreateRoleModel createRoleModel() {
        return Instancio.of(CreateRoleModel.class)
                .create();
    }

    public static ChangeUserRoleDto changeUserRoleDto(Set<String> roles) {
        return Instancio.of(ChangeUserRoleDto.class)
                .set(Select.field(ChangeUserRoleDto::roles), roles)
                .create();
    }

    public static String string() {
        return Instancio.create(String.class);
    }
}
