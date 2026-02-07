package kg.attractor.javasharehub.exceptions;

import java.util.NoSuchElementException;

public class MyFileNotFoundException extends NoSuchElementException {
    public MyFileNotFoundException(){super("Файл не найден");}
}
