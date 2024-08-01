package ru.aristi.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ru.aristi.task.exception.RoleNotFoundException;
import ru.aristi.task.model.entity.Role;

import java.util.Optional;
import java.util.Set;

/**
 * Репозиторий для работы с сущностью {@link Role}.
 */
public interface RoleRepository extends JpaRepository<Role, Short> {
    /**
     * Найти роль по имени.
     *
     * @param name имя роли.
     * @return {@link Optional}, содержащий найденную роль, если она существует, иначе пустой Optional.
     */
    Optional<Role> findByName(String name);

    /**
     * Найти роли по списку имен.
     *
     * @param names список имен ролей.
     * @return найденные роли.
     */
    Set<Role> findByNameIn(Set<String> names);

    /**
     * Удалить роль по имени.
     *
     * @param name имя роли для удаления.
     */
    @Modifying
    void deleteByName(String name);

    /**
     * Получает роль по ее имени или бросает исключение, если роль не найдена.
     *
     * @param name имя роли.
     * @return {@link Role} - найденная роль.
     * @throws RoleNotFoundException если роль не найдена.
     */
    default Role findByIdOrElseThrow(String name) {
        return findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(String.format(
                        "Role with name '%s' not found",
                        name
                )));
    }
}
