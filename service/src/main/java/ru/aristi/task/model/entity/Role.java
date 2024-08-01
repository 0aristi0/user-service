package ru.aristi.task.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Сущность Роли таблицы {@link Role}.
 */
@Entity
@Data
@Table(name = "roles")
public class Role {

    /**
     * ID роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /**
     * Имя роли.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
