package ru.aristi.task.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.aristi.task.config.AbstractIntegrationTest;
import ru.aristi.task.model.dto.role.CreateRoleDto;
import ru.aristi.task.model.dto.role.RoleDto;
import ru.aristi.task.model.dto.role.UpdateRoleDto;
import ru.aristi.task.provider.TestDataProvider;
import ru.aristi.task.repository.RoleRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleControllerTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RoleRepository repository;

    @AfterEach
    void setUp() {
        repository.deleteAllInBatch();
    }

    @Test
    void createRoleSuccessTest() throws Exception {
        CreateRoleDto dto = TestDataProvider.createRoleDto();

        RoleDto result = create(dto);

        assertThat(dto.name()).isEqualTo(result.name());
    }

    @Test
    void getRoleSuccessTest() throws Exception {
        RoleDto roleDto = create(TestDataProvider.createRoleDto());

        RoleDto result = getRole(roleDto.name());

        assertThat(result).isEqualTo(roleDto);
    }

    @Test
    void getAllRolesSuccessTest() throws Exception {
        CreateRoleDto createRoleDto1 = TestDataProvider.createRoleDto();
        CreateRoleDto createRoleDto2 = TestDataProvider.createRoleDto();

        create(createRoleDto1);
        create(createRoleDto2);

        MvcResult result = mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, RoleDto.class);

        List<RoleDto> responseList = objectMapper.readValue(jsonResponse, type);
    }

    @Test
    void testUpdateRole() throws Exception {
        CreateRoleDto createRoleDto = TestDataProvider.createRoleDto();
        RoleDto createdRoleDto = create(createRoleDto);

        UpdateRoleDto updateRoleDto = TestDataProvider.updateRoleDto();

        MvcResult result = mockMvc.perform(put("/api/v1/roles/{name}", createdRoleDto.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRoleDto)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        RoleDto responseDto = objectMapper.readValue(jsonResponse, RoleDto.class);

        assertThat(updateRoleDto.name()).isEqualTo(responseDto.name());
    }

    @Test
    void testDeleteRole() throws Exception {
        CreateRoleDto createRoleDto = TestDataProvider.createRoleDto();
        RoleDto createdRoleDto = create(createRoleDto);

        mockMvc.perform(delete("/api/v1/roles/{name}", createdRoleDto.name()))
                .andExpect(status().isNoContent());
    }

    private RoleDto create(CreateRoleDto dto) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        return objectMapper.readValue(jsonResponse, RoleDto.class);
    }

    private RoleDto getRole(String name) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/roles/" + name))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        return objectMapper.readValue(jsonResponse, RoleDto.class);
    }
}
