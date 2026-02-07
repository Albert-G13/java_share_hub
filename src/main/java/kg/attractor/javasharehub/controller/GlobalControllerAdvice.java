package kg.attractor.javasharehub.controller;

import kg.attractor.javasharehub.exceptions.IllegalFileException;
import kg.attractor.javasharehub.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("isAuth")
    public boolean isAuth(Principal principal) {
        return principal != null;
    }

    @ModelAttribute("username")
    public String username(Principal principal) {
        return (principal != null) ? principal.getName() : null;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound( Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        return "errors/error";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserExists( Model model) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        return "errors/error";
    }

    @ExceptionHandler(IllegalFileException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleIllegalFile(Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        return "errors/error";
    }
}
