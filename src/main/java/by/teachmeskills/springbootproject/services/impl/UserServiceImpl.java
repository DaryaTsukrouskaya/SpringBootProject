package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryServiceImpl categoryRepository, ProductServiceImpl productService,
                           OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryService = categoryRepository;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public ModelAndView registerUser(User user, String repPassword) {
        ModelMap modelMap = new ModelMap();
        if (repPassword.equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of(Role.builder().id(2).name("USER").build()));
            userRepository.save(user);
            modelMap.addAttribute("categories", categoryService.read());
            return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);
        } else {
            modelMap.addAttribute("state", "Пароли не совпадают");
            return new ModelAndView(PagesPathEnum.REGISTER_PAGE.getPath(), modelMap);
        }
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public ModelAndView authenticate() {
        ModelMap modelMap = new ModelMap();
        return new ModelAndView(PagesPathEnum.SIGN_IN_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView userServicePage(User user, PaginationParams paginationParams) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("user", user);
        if (orderRepository.findByUserId(user.getId()).isEmpty()) {
            return new ModelAndView(PagesPathEnum.USER_PROFILE_PAGE.getPath(), modelMap);
        }
        if (paginationParams.getPageNumber() < 0) {
            paginationParams.setPageNumber(0);
        }
        Pageable pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("orderDate").descending());
        List<Order> orders = orderRepository.findByUserId(user.getId(), pageable).getContent();
        if (orders.isEmpty()) {
            paginationParams.setPageNumber(paginationParams.getPageNumber() - 1);
            pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("orderDate").descending());
            orders = orderRepository.findByUserId(user.getId(), pageable).getContent();
        }
        modelMap.addAttribute("userOrders", orders);
        return new ModelAndView(PagesPathEnum.USER_PROFILE_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView checkout(Cart cart) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("cart", cart);
        return new ModelAndView(PagesPathEnum.CHECKOUT_PAGE.getPath(), modelMap);
    }

    @Override
    public void update(User user) {
        User updatedUser = userRepository.findById(user.getId());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setOrders(user.getOrders());
        userRepository.save(updatedUser);
    }

    @Override
    public User getCurrentUser() {
        User user = null;
        Object principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            user = userRepository.findByEmail(username);
        }
        return user;
    }

}
