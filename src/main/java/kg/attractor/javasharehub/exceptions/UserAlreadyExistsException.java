package kg.attractor.javasharehub.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Пользователь с данным email уже существует");
    }
}
