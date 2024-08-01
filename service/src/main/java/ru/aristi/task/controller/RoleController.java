package ru.aristi.task.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aristi.task.controller.swagger.RoleControllerApi;
import ru.aristi.task.mapper.RoleMapper;
import ru.aristi.task.model.business.role.CreateRoleModel;
import ru.aristi.task.model.business.role.RoleModel;
import ru.aristi.task.model.business.role.UpdateRoleModel;
import ru.aristi.task.model.dto.role.CreateRoleDto;
import ru.aristi.task.model.dto.role.RoleDto;
import ru.aristi.task.model.dto.role.UpdateRoleDto;
import ru.aristi.task.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
class RoleController implements RoleControllerApi {

    private final RoleService service;
    private final RoleMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto createRole(@RequestBody @Valid CreateRoleDto dto) {
        CreateRoleModel model = mapper.toModel(dto);

        RoleModel createdRole = service.create(model);

        return mapper.toDto(createdRole);
    }

    @GetMapping
    public List<RoleDto> getAllRoles() {
        List<RoleModel> models = service.findAll();

        return mapper.toDtos(models);
    }

    @GetMapping("/{name}")
    public RoleDto getRole(@PathVariable String name) {
        RoleModel model = service.findByNameOrElseThrow(name);

        return mapper.toDto(model);
    }

    @PutMapping("/{name}")
    public RoleDto updateRole(@PathVariable String name, @RequestBody @Valid UpdateRoleDto dto) {
        UpdateRoleModel model = mapper.toModel(dto);

        RoleModel updatedRole = service.update(name, model);

        return mapper.toDto(updatedRole);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable String name) {
        service.deleteByName(name);
    }
}
