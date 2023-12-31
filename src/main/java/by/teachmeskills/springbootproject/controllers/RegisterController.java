package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView registerPage() {
        return new ModelAndView(PagesPathEnum.REGISTER_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView registerUser(@Valid @ModelAttribute("user") User user, @RequestParam("repeatPassword") String repPass) {
        return userService.registerUser(user, repPass);
    }
}
