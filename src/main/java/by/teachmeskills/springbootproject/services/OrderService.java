package by.teachmeskills.springbootproject.services;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.NoOrderAddressException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

public interface OrderService extends BaseService<Order> {
    Optional<Order> findById(int id);

    List<Order> findByUserId(int id);

    ModelAndView createUserOrder(User user, Cart cart, String address) throws NoOrderAddressException;
}
