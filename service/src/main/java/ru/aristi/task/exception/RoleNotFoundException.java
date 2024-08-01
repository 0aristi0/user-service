package ru.aristi.task.exception;

/**
 * Исключение - роль не найдена.
 */
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
