package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/profile")
@SessionAttributes({"paginationParams"})
public class UserProfileController {
    private final UserService userService;

    public UserProfileController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    public ModelAndView getUserPage(@ModelAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        return userService.userProfilePage(userService.getCurrentUser(), paginationParams);
    }

    @GetMapping("/pagination/{page}")
    public ModelAndView userPagePaginated(@PathVariable int page, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(page);
        return userService.userProfilePage(userService.getCurrentUser(), paginationParams);
    }

    @GetMapping("/changeSize/{size}")
    public ModelAndView changePage(@PathVariable int size, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageSize(size);
        return userService.userProfilePage(userService.getCurrentUser(), paginationParams);
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }
}
