package kg.attractor.javasharehub.service;

import kg.attractor.javasharehub.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
}
