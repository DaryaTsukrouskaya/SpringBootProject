package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    ModelAndView registerUser(User user, String repPassword);

    User findById(int id);

    ModelAndView authenticate(String email, String password);

    ModelAndView userServicePage(User user);

    ModelAndView checkout(Cart cart);

    void update(User user);

}
