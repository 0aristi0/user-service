package ru.aristi.task.exception;

/**
 * Исключение - пользователь не найдена.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
