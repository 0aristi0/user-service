package ru.aristi.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aristi.task.exception.RoleNotFoundException;
import ru.aristi.task.mapper.RoleMapper;
import ru.aristi.task.model.business.role.CreateRoleModel;
import ru.aristi.task.model.business.role.RoleModel;
import ru.aristi.task.model.business.role.UpdateRoleModel;
import ru.aristi.task.model.entity.Role;
import ru.aristi.task.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ролями.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    /**
     * Создает новую роль.
     *
     * @param model роль для создания.
     * @return {@link RoleModel} - созданная роль.
     */
    public RoleModel create(CreateRoleModel model) {
        Role role = repository.save(mapper.toEntity(model));

        return mapper.toModel(role);
    }

    /**
     * Получает роль по ее имени.
     *
     * @param name имя роли.
     * @return {@link Optional} найденная {@link RoleModel}, если существует.
     */
    public Optional<RoleModel> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toModel);
    }

    /**
     * Получает роль по ее имени или бросает исключение, если роль не найдена.
     *
     * @param name имя роли.
     * @return {@link RoleModel} - найденная роль.
     * @throws RoleNotFoundException если роль не найдена.
     */
    public RoleModel findByNameOrElseThrow(String name) {
        Role role = repository.findByIdOrElseThrow(name);

        return mapper.toModel(role);
    }

    public List<RoleModel> findAll() {
        return mapper.toModels(repository.findAll());
    }

    /**
     * Обновляет роль по ее имени.
     *
     * @param name  имя существующей роли для обновления.
     * @param model обновленные данные роли.
     * @return {@link RoleModel} - обновленная роль.
     * @throws RoleNotFoundException если роль не найдена.
     */
    public RoleModel update(String name, UpdateRoleModel model) {
        Role role = repository.findByIdOrElseThrow(name);

        role.setName(model.name());

        return mapper.toModel(repository.save(role));
    }

    /**
     * Удаляет роль по ее имени.
     *
     * @param name имя роли для удаления.
     */
    @Transactional
    public void deleteByName(String name) {
        Role role = repository.findByIdOrElseThrow(name);
        repository.delete(role);
    }
}
