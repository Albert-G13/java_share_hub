package kg.attractor.javasharehub.controller;
import kg.attractor.javasharehub.service.CategoryService;
import kg.attractor.javasharehub.service.FileService;
import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final FileService fileService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public String showProfile(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("user", userService.findByEmail(email));
        model.addAttribute("files", fileService.findAllByOwnerEmail(email));
        model.addAttribute("categories", categoryService.findAll());
        return "user/profile";
    }
}