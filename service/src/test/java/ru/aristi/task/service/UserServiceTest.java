package ru.aristi.task.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aristi.task.config.AbstractIntegrationTest;
import ru.aristi.task.exception.UserNotFoundException;
import ru.aristi.task.mapper.UserMapper;
import ru.aristi.task.model.business.user.CreateUserModel;
import ru.aristi.task.model.business.user.UpdateUserModel;
import ru.aristi.task.model.business.user.UserModel;
import ru.aristi.task.model.entity.Role;
import ru.aristi.task.provider.TestDataProvider;
import ru.aristi.task.repository.RoleRepository;
import ru.aristi.task.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest extends AbstractIntegrationTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper mapper;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        roleRepository.deleteAllInBatch();
    }

    @Test
    void testCreateUser() {
        CreateUserModel createUserModel = TestDataProvider.createUserModel();

        UserModel createdUser = service.create(createUserModel);

        assertNotNull(createdUser);

        assertEquals(createUserModel.username(), createdUser.username());
        assertEquals(createUserModel.email(), createdUser.email());
        assertEquals(createUserModel.name(), createdUser.name());
        assertEquals(createUserModel.lastName(), createdUser.lastName());
    }

    @Test
    void testFindUserById() {
        CreateUserModel createUserModel = TestDataProvider.createUserModel();
        UserModel createdUser = service.create(createUserModel);

        Optional<UserModel> foundUser = service.findById(createdUser.id());
        assertTrue(foundUser.isPresent());
        assertEquals(createdUser.id(), foundUser.get().id());
    }

    @Test
    void testUpdateUser() {
        CreateUserModel createUserModel = TestDataProvider.createUserModel();
        UserModel createdUser = service.create(createUserModel);

        UpdateUserModel updateUserModel = TestDataProvider.updateUserModel();
        UserModel updatedUser = service.update(createdUser.id(), updateUserModel);

        assertNotNull(updatedUser);

        assertEquals(updateUserModel.name(), updatedUser.name());
        assertEquals(updateUserModel.lastName(), updatedUser.lastName());
        assertEquals(updateUserModel.email(), updatedUser.email());
    }

    @Test
    void testUpdateUserRoles() {
        CreateUserModel createUserModel = TestDataProvider.createUserModel();
        UserModel createdUser = service.create(createUserModel);

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        UserModel updatedUser = service.updateRoles(createdUser.id(), Set.of("ROLE_USER", "ROLE_ADMIN"));

        assertNotNull(updatedUser);
        assertEquals(2, updatedUser.roles().size());
        assertTrue(updatedUser.roles().stream().anyMatch(role -> role.name().equals("ROLE_USER")));
        assertTrue(updatedUser.roles().stream().anyMatch(role -> role.name().equals("ROLE_ADMIN")));
    }

    @Test
    void testBanAndUnbanUser() {
        CreateUserModel createUserModel = TestDataProvider.createUserModel();
        UserModel userModel = service.create(createUserModel);

        assertFalse(userModel.isBanned());

        service.ban(userModel.id());

        UserModel bannedUser = service.findByIdOrElseThrow(userModel.id());
        assertTrue(bannedUser.isBanned());

        service.unban(userModel.id());

        UserModel unbannedUser = service.findByIdOrElseThrow(userModel.id());
        assertFalse(unbannedUser.isBanned());
    }

    @Test
    void testDeleteUser() {
        CreateUserModel createUserModel = TestDataProvider.createUserModel();
        UserModel createdUser = service.create(createUserModel);

        service.delete(createdUser.id());

        Optional<UserModel> deletedUser = service.findById(createdUser.id());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testFindByIdOrElseThrowNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> service.findByIdOrElseThrow(randomId));
    }

    @Test
    void testUpdateUserRolesNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> service.updateRoles(randomId, Set.of("ROLE_USER", "ROLE_ADMIN")));
    }

    @Test
    void testBanUserNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> service.ban(randomId));
    }

    @Test
    void testUnbanUserNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> service.unban(randomId));
    }
}
