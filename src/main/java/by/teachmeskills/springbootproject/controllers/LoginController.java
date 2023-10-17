package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView login() {
        return userService.authenticate();
    }
}
