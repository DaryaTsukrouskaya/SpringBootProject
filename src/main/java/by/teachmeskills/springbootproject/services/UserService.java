package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.User;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    ModelAndView registerUser(User user, String repPassword);

    User findById(int id);

    ModelAndView authenticate(boolean error);

    ModelAndView userProfilePage(User user, PaginationParams paginationParams);

    ModelAndView checkout(Cart cart);

    void update(User user);

    User getCurrentUser();

}
