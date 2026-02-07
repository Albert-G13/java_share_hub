package kg.attractor.javasharehub.exceptions;

import java.util.NoSuchElementException;

public class CategoryNotFoundException extends NoSuchElementException {
    public CategoryNotFoundException(){super("Категория не найдена");}
}
