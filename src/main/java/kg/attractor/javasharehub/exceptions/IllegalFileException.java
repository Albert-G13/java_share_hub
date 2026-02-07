package kg.attractor.javasharehub.exceptions;

public class IllegalFileException extends IllegalArgumentException {
    public IllegalFileException() {
        super("Доступ к чужим файлам запрещён");
    }
}
