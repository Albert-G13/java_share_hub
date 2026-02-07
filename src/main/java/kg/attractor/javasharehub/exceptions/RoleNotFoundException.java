package kg.attractor.javasharehub.exceptions;

import java.util.NoSuchElementException;

public class RoleNotFoundException extends NoSuchElementException {
    public RoleNotFoundException() {
        super("Роль не найдена");
    }
}
