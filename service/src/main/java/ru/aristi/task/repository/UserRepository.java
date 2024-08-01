package ru.aristi.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.aristi.task.exception.UserNotFoundException;
import ru.aristi.task.model.entity.User;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link User}.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Устанавливает значение поля is_banned в true для пользователя с указанным ID.
     *
     * @param id идентификатор пользователя.
     */
    @Modifying
    @Query("""
            UPDATE User u SET u.isBanned = true WHERE u.id = :id
            """)
    void banById(UUID id);

    /**
     * Устанавливает значение поля is_banned в false для пользователя с указанным ID.
     *
     * @param id идентификатор пользователя.
     */
    @Modifying
    @Query("""
            UPDATE User u SET u.isBanned = false WHERE u.id = :id
            """)
    void unbanById(UUID id);

    /**
     * Получает пользователя по его ID или бросает исключение, если пользователь не найден.
     *
     * @param id пользователя.
     * @return найденный {@link User}.
     * @throws UserNotFoundException если пользователь не найден.
     */
    default User findByIdOrElseThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "User with name '%s' not found",
                        id
                )));
    }
}
