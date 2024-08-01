package ru.aristi.task.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * Cущность пользователя таблицы {@link User}.
 */
@Entity
@Data
@Table(name = "users")
public class User {

    /**
     * ID пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    /**
     * Логин пользователя.
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Email пользователя.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Имя пользователя.
     */
    @Column(name = "name")
    private String name;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Бан пользователся.
     */
    @Column(name = "is_banned")
    private boolean isBanned;

    /**
     * Роли пользователя.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}
