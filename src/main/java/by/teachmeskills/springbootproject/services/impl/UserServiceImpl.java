package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.repositories.impl.UserRepositoryImpl;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository, CategoryServiceImpl categoryRepository, ProductServiceImpl productService) {
        this.userRepository = userRepository;
        this.categoryService = categoryRepository;
        this.productService = productService;
    }

    @Override
    public List<User> read() throws DBConnectionException {
        return userRepository.read();
    }

    @Override
    public void create(User user) throws DBConnectionException, UserAlreadyExistsException {
        userRepository.create(user);
    }

    @Override
    public ModelAndView registerUser(User user, String repPassword) throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        if (repPassword.equals(user.getPassword())) {
            try {
                userRepository.create(user);
                modelMap.addAttribute("categories", categoryService.read());
            } catch (UserAlreadyExistsException e) {
                modelMap.addAttribute("state", "Пользователь с такой почтой уже существует");
                return new ModelAndView(PagesPathEnum.REGISTER_PAGE.getPath(), modelMap);
            }
        }
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        userRepository.delete(id);

    }

    @Override
    public User findById(int id) throws DBConnectionException {
        return userRepository.findById(id);
    }

    @Override
    public ModelAndView authenticate(String email, String password) throws DBConnectionException {
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
    public void updatePassword(String password, String email) throws DBConnectionException {
        userRepository.updatePassword(password, email);

    }

    @Override
    public void updateEmail(String previousEmail, String newEmail) throws DBConnectionException {
        userRepository.updateEmail(previousEmail, newEmail);

    }

    @Override
    public ModelAndView userServicePage(User user) throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("user", user);
        List<Product> productList1 = new ArrayList<>();
        List<Product> productList2 = new ArrayList<>();
        productList1.add(productService.findById(1));
        productList1.add(productService.findById(2));
        productList1.add(productService.findById(3));
        productList1.add(productService.findById(4));
        productList2.add(productService.findById(3));
        productList2.add(productService.findById(1));

        Order order1 = new Order(1, LocalDateTime.now(), productList1);
        Order order2 = new Order(2, LocalDateTime.now(), productList2);
        List<Order> userOrders = new ArrayList<>();
        userOrders.add(order1);
        userOrders.add(order2);
        modelMap.addAttribute("userOrders", userOrders);
        return new ModelAndView(PagesPathEnum.USER_PROFILE_PAGE.getPath(), modelMap);
    }
}
