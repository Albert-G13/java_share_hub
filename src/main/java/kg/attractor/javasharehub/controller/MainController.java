package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.service.CategoryService;
import kg.attractor.javasharehub.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MainController {
    private final CategoryService categoryService;
    private final FileService fileService;

    @GetMapping("/")
    public String indexPage(@RequestParam(required = false) Long categoryId, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        if (categoryId != null) {
            model.addAttribute("files", fileService.findAllByStatusAndCategoryId("public", categoryId));
            model.addAttribute("selectedCategory", categoryId);
        } else {
            model.addAttribute("files", fileService.findAllByStatus("public"));
        }
        return "index";
    }
}
