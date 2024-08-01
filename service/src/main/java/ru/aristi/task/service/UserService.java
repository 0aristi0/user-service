package ru.aristi.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aristi.task.exception.UserNotFoundException;
import ru.aristi.task.mapper.UserMapper;
import ru.aristi.task.model.business.user.CreateUserModel;
import ru.aristi.task.model.business.user.UpdateUserModel;
import ru.aristi.task.model.business.user.UserModel;
import ru.aristi.task.model.entity.Role;
import ru.aristi.task.model.entity.User;
import ru.aristi.task.repository.RoleRepository;
import ru.aristi.task.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Сервис для работы c пользователем.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    /**
     * Создает нового пользователя.
     *
     * @param model пользователь для создания.
     * @return {@link UserModel} - созданный пользователь.
     */
    public UserModel create(CreateUserModel model) {
        User createdUser = repository.save(mapper.toEntity(model));

        return mapper.toModel(createdUser);
    }

    /**
     * Получает пользователя по его ID.
     *
     * @param id пользователя.
     * @return {@link Optional} найденный {@link UserModel}, если существует.
     */
    public Optional<UserModel> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toModel);
    }

    /**
     * Получает пользователя по его ID или бросает исключение, если пользователь не найден.
     *
     * @param id пользователя.
     * @return найденный {@link UserModel}.
     * @throws UserNotFoundException если пользователь не найден.
     */
    public UserModel findByIdOrElseThrow(UUID id) {
        User user = repository.findByIdOrElseThrow(id);

        return mapper.toModel(user);
    }

    /**
     * Обновляет данные пользователя по его ID.
     *
     * @param id    пользователя для обновления.
     * @param model обновленные данные пользователя.
     * @return обновленный {@link UserModel}.
     * @throws UserNotFoundException если пользователь не найден.
     */
    public UserModel update(UUID id, UpdateUserModel model) {
        User user = repository.findByIdOrElseThrow(id);

        user.setName(model.name());
        user.setLastName(model.lastName());
        user.setEmail(model.email());

        return mapper.toModel(repository.save(user));
    }

    /**
     * Обновляет роли пользователя по его ID.
     *
     * @param id    пользователя для обновления ролей.
     * @param names набор имен ролей для установки.
     * @return обновленный {@link UserModel}.
     * @throws UserNotFoundException если пользователь не найден.
     */
    public UserModel updateRoles(UUID id, Set<String> names) {
        User user = repository.findByIdOrElseThrow(id);
        Set<Role> roles = roleRepository.findByNameIn(names);

        user.setRoles(roles);

        return mapper.toModel(repository.save(user));
    }

    /**
     * Блокирует пользователя по его ID.
     *
     * @param id пользователя для блокировки.
     */
    @Transactional
    public void ban(UUID id) {
        User user = repository.findByIdOrElseThrow(id);

        user.setBanned(true);
    }

    /**
     * Разблокирует пользователя по его ID.
     *
     * @param id пользователя для разблокировки.
     */
    @Transactional
    public void unban(UUID id) {
        User user = repository.findByIdOrElseThrow(id);

        user.setBanned(false);
    }

    /**
     * Удаляет пользователя по его ID.
     *
     * @param id пользователя для удаления.
     */
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
