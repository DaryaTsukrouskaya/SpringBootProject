package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.PaginationParams;
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
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryServiceImpl categoryRepository, ProductServiceImpl productService,
                           OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryRepository;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public ModelAndView registerUser(User user, String repPassword) {
        ModelMap modelMap = new ModelMap();
        if (repPassword.equals(user.getPassword())) {
            userRepository.save(user);
            modelMap.addAttribute("categories", categoryService.read());
        }
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);
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
    public ModelAndView authenticate(String email, String password) {
        ModelMap modelMap = new ModelMap();
        User user;
        if (email != null && password != null) {
            user = userRepository.findByEmailAndPassword(email, password);
            if (Optional.ofNullable(user).isPresent()) {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("categories", categoryService.read());
            } else {
                modelMap.addAttribute("state", "Неверный логин или пароль");
                return new ModelAndView(PagesPathEnum.SIGN_IN_PAGE.getPath(), modelMap);
            }
        } else {
            modelMap.addAttribute("state", "Заполните все поля формы");
            return new ModelAndView(PagesPathEnum.SIGN_IN_PAGE.getPath(), modelMap);
        }
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);
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
        userRepository.save(updatedUser);
    }
}
