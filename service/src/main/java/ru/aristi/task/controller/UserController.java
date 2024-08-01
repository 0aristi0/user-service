package ru.aristi.task.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aristi.task.controller.swagger.UserControllerApi;
import ru.aristi.task.mapper.UserMapper;
import ru.aristi.task.model.business.user.CreateUserModel;
import ru.aristi.task.model.business.user.UserModel;
import ru.aristi.task.model.dto.user.ChangeUserRoleDto;
import ru.aristi.task.model.dto.user.CreateUserDto;
import ru.aristi.task.model.dto.user.UpdateUserDto;
import ru.aristi.task.model.dto.user.UserDto;
import ru.aristi.task.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
class UserController implements UserControllerApi {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid CreateUserDto dto) {
        CreateUserModel model = userMapper.toModel(dto);

        UserModel createdUser = userService.create(model);

        return userMapper.toDto(createdUser);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id) {
        UserModel model = userService.findByIdOrElseThrow(id);

        return userMapper.toDto(model);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable UUID id, @RequestBody @Valid UpdateUserDto dto) {
        UserModel model = userService.update(id, userMapper.toModel(dto));

        return userMapper.toDto(model);
    }

    @PatchMapping("/{id}/roles")
    public UserDto updateUserRoles(@PathVariable UUID id, @RequestBody ChangeUserRoleDto dto) {
        UserModel model = userService.updateRoles(id, dto.roles());

        return userMapper.toDto(model);
    }

    @PatchMapping("/{id}/ban")
    public void banUser(@PathVariable UUID id) {
        userService.ban(id);
    }

    @PatchMapping("/{id}/unban")
    public void unbanUser(@PathVariable UUID id) {
        userService.unban(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }
}
