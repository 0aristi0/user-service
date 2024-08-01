package ru.aristi.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.aristi.task.config.AbstractIntegrationTest;
import ru.aristi.task.model.dto.role.RoleDto;
import ru.aristi.task.model.dto.user.ChangeUserRoleDto;
import ru.aristi.task.model.dto.user.CreateUserDto;
import ru.aristi.task.model.dto.user.UpdateUserDto;
import ru.aristi.task.model.dto.user.UserDto;
import ru.aristi.task.model.entity.Role;
import ru.aristi.task.provider.TestDataProvider;
import ru.aristi.task.repository.RoleRepository;
import ru.aristi.task.repository.UserRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository repository;
    @Autowired
    RoleRepository roleRepository;

    @AfterEach
    void setUp() {
        repository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();
    }

    @Test
    void createUserTest() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();

        UserDto responseDto = create(createUserDto);

        assertNotNull(responseDto.id());
        assertEquals(createUserDto.username(), responseDto.username());
        assertEquals(createUserDto.email(), responseDto.email());
    }

    @Test
    void getUserTest() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();
        UserDto createdUserDto = create(createUserDto);

        UserDto responseDto = getUser(createdUserDto.id());

        assertEquals(createdUserDto.id(), responseDto.id());
        assertEquals(createdUserDto.username(), responseDto.username());
        assertEquals(createdUserDto.email(), responseDto.email());
        assertEquals(createdUserDto.name(), responseDto.name());
        assertEquals(createdUserDto.lastName(), responseDto.lastName());
    }

    @Test
    void updateUserTest() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();
        UserDto createdUserDto = create(createUserDto);

        UpdateUserDto updateUserDto = TestDataProvider.updateUserDto();

        MvcResult result = mockMvc.perform(put("/api/v1/users/{id}", createdUserDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        UserDto responseDto = objectMapper.readValue(jsonResponse, UserDto.class);

        assertEquals(updateUserDto.name(), responseDto.name());
        assertEquals(updateUserDto.email(), responseDto.email());
    }

    @Test
    void updateUserRolesTest() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();
        UserDto createdUserDto = create(createUserDto);
        Set<String> roles = createRoles(5);

        ChangeUserRoleDto changeUserRoleDto = TestDataProvider.changeUserRoleDto(roles);

        MvcResult result = mockMvc.perform(patch("/api/v1/users/{id}/roles", createdUserDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeUserRoleDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        UserDto responseDto = objectMapper.readValue(jsonResponse, UserDto.class);

        assertThat(responseDto.roles().stream()
                .map(RoleDto::name)
                .toList())
                .containsExactlyInAnyOrder(changeUserRoleDto.roles().toArray(String[]::new));
    }

    @Test
    void testBanUser() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();
        UserDto userDto = create(createUserDto);

        mockMvc.perform(patch("/api/v1/users/{id}/ban", userDto.id()))
                .andExpect(status().isOk())
                .andReturn();

        UserDto responseDto = getUser(userDto.id());
        assertThat(responseDto.isBanned()).isTrue();
    }

    @Test
    void testUnbanUser() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();
        UserDto createdUserDto = create(createUserDto);

        mockMvc.perform(patch("/api/v1/users/{id}/ban", createdUserDto.id()))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(patch("/api/v1/users/{id}/unban", createdUserDto.id()))
                .andExpect(status().isOk())
                .andReturn();

        UserDto responseDto = getUser(createdUserDto.id());
        assertThat(responseDto.isBanned()).isFalse();
    }

    @Test
    void testDeleteUser() throws Exception {
        CreateUserDto createUserDto = TestDataProvider.createUserDto();
        UserDto userDto = create(createUserDto);

        mockMvc.perform(delete("/api/v1/users/{id}", userDto.id()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(repository.findById(userDto.id())).isNotPresent();
    }


    private UserDto create(CreateUserDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, UserDto.class);
    }

    private UserDto getUser(UUID id) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/users/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, UserDto.class);
    }

    private Set<String> createRoles(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createRole(TestDataProvider.string()))
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    private Role createRole(String roleName) {
        Role role = new Role();

        role.setName(roleName);

        return roleRepository.save(role);
    }

}
