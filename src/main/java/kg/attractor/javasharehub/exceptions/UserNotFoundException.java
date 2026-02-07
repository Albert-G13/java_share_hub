package kg.attractor.javasharehub.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(){super("Пользователь не найден");}
}
