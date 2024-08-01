package ru.aristi.task.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aristi.task.config.AbstractIntegrationTest;
import ru.aristi.task.exception.RoleNotFoundException;
import ru.aristi.task.model.business.role.CreateRoleModel;
import ru.aristi.task.model.business.role.RoleModel;
import ru.aristi.task.model.business.role.UpdateRoleModel;
import ru.aristi.task.model.entity.Role;
import ru.aristi.task.provider.TestDataProvider;
import ru.aristi.task.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleServiceTest extends AbstractIntegrationTest {

    @Autowired
    private RoleService service;

    @Autowired
    private RoleRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAllInBatch();

    }

    @Test
    void testCreateRole() {
        CreateRoleModel createRoleModel = TestDataProvider.createRoleModel();

        RoleModel createdRole = service.create(createRoleModel);

        assertNotNull(createdRole);

        assertEquals(createRoleModel.name(), createdRole.name());

        Optional<RoleModel> foundRole = service.findByName(createRoleModel.name());
        assertTrue(foundRole.isPresent());
        assertEquals(createRoleModel.name(), foundRole.get().name());
    }

    @Test
    void testFindByName() {
        CreateRoleModel createRoleModel = TestDataProvider.createRoleModel();
        service.create(createRoleModel);

        Optional<RoleModel> foundRole = service.findByName(createRoleModel.name());

        assertTrue(foundRole.isPresent());
        assertEquals(createRoleModel.name(), foundRole.get().name());
    }

    @Test
    void testFindByNameOrElseThrow() {
        CreateRoleModel createRoleModel = TestDataProvider.createRoleModel();
        service.create(createRoleModel);

        RoleModel foundRole = service.findByNameOrElseThrow(createRoleModel.name());

        assertNotNull(foundRole);
        assertEquals(createRoleModel.name(), foundRole.name());
    }

    @Test
    void testFindByNameOrElseThrow_NotFound() {
        assertThrows(RoleNotFoundException.class, () -> service.findByNameOrElseThrow("ROLE_UNKNOWN"));
    }

    @Test
    void testUpdateRole() {
        CreateRoleModel createRoleModel = TestDataProvider.createRoleModel();
        service.create(createRoleModel);

        UpdateRoleModel updateRoleModel = TestDataProvider.updateRoleModel();
        RoleModel updatedRole = service.update(createRoleModel.name(), updateRoleModel);

        assertNotNull(updatedRole);
        assertEquals(updateRoleModel.name(), updatedRole.name());

        RoleModel foundRole = service.findByNameOrElseThrow(updateRoleModel.name());
        assertEquals(updateRoleModel.name(), foundRole.name());
    }

    @Test
    void testDeleteByName() {
        Role role = new Role();
        role.setName("ROLE_USER");
        repository.save(role);

        service.deleteByName("ROLE_USER");

        assertFalse(repository.findByName("ROLE_USER").isPresent());
    }

    @Test
    void testFindAllRoles() {
        CreateRoleModel role = TestDataProvider.createRoleModel();
        CreateRoleModel model = TestDataProvider.createRoleModel();
        service.create(role);
        service.create(model);

        List<RoleModel> roles = service.findAll();

        assertNotNull(roles);
        assertEquals(2, roles.size());
    }

    @Test
    void testUpdateRoleNotFound() {
        UpdateRoleModel updateRoleModel = TestDataProvider.updateRoleModel();
        assertThrows(RoleNotFoundException.class, () -> service.update("ROLE_UNKNOWN", updateRoleModel));
    }

    @Test
    void testDeleteByNameNotFound() {
        assertThrows(RoleNotFoundException.class, () -> service.deleteByName("ROLE_UNKNOWN"));
    }
}