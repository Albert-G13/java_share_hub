package kg.attractor.javasharehub.service.impl;

import kg.attractor.javasharehub.dto.CategoryDto;
import kg.attractor.javasharehub.model.Category;
import kg.attractor.javasharehub.repository.CategoryRepository;
import kg.attractor.javasharehub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setCategory(category.getCategory());
            categoryDtos.add(dto);
        });
        return categoryDtos;
    }
}
