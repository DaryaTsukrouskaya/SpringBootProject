package by.teachmeskills.springbootproject.services.impl;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.NoOrderAddressException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userService, CategoryServiceImpl categoryService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Order> read() {
        return orderRepository.findAll();
    }

    @Override
    public void create(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(int id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public ModelAndView createUserOrder(User user, Cart cart, String address) throws NoOrderAddressException {
        if (address.isBlank()) {
            throw new NoOrderAddressException("Введите адрес для заказа!");
        }
        Order order = new Order(cart.getTotalPrice(), LocalDateTime.now(), user, address, cart.getProducts());
        orderRepository.save(order);
        if (user.getOrders() == null || user.getOrders().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            user.setOrders(orders);
        }
        user.getOrders().add(order);
        userService.update(user);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("categories", categoryService.read());
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);
    }
}
