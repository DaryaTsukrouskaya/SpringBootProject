package by.teachmeskills.springbootproject;

import by.teachmeskills.springbootproject.converters.ProductConverter;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.NoOrderAddressException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.CustomUserDetailsService;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.impl.CategoryServiceImpl;
import by.teachmeskills.springbootproject.services.impl.OrderServiceImpl;
import by.teachmeskills.springbootproject.services.impl.ProductServiceImpl;
import by.teachmeskills.springbootproject.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationServicesTests {
    private static UserRepository userRepositoryMock = mock(UserRepository.class);
    private static CategoryService categoryServiceMock = mock(CategoryService.class);
    private static ProductService productServiceMock = mock(ProductService.class);
    private static OrderRepository orderRepository = mock(OrderRepository.class);
    private static CategoryRepository categoryRepositoryMock = mock(CategoryRepository.class);
    private static PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private static UserService userServiceMock = mock(UserService.class);
    private static ProductRepository productRepository = mock(ProductRepository.class);
    private static ProductConverter productConverter = mock(ProductConverter.class);
    private static HttpServletResponse response = mock(HttpServletResponse.class);
    private static CustomUserDetailsService customUserDetailsService;
    private static UserService userService;
    private static OrderService orderService;
    private static ProductService productService;
    private static CategoryService categoryService;
    private static User testUser;
    private static String repPassword;
    private static Product product;
    private static Category category = new Category();
    private static Cart cart = new Cart();
    private static ArrayList<Category> categories;

    @BeforeAll
    public static void setUp() {
        userService = new UserServiceImpl(userRepositoryMock, categoryServiceMock, productServiceMock, orderRepository, passwordEncoder);
        orderService = new OrderServiceImpl(orderRepository, userServiceMock, categoryServiceMock);
        product = new Product("колготки 2d", "классические", "tights.png", new Category(), new BigDecimal(100));
        testUser = new User("darya", "tsukrouskaya", LocalDate.of(2001, 10, 31), "darya.raikhert.31@mail.ru", "12345678", null, null);
        testUser.setId(10);
        testUser.setRoles(List.of(Role.builder().id(2).name("USER").build()));
        repPassword = "12345678";
        cart.addProduct(product);
        productService = new ProductServiceImpl(productRepository, productConverter, categoryServiceMock);
        categoryService = new CategoryServiceImpl(categoryRepositoryMock);
        customUserDetailsService=new CustomUserDetailsService(userRepositoryMock);
        categories = new ArrayList<>();
        category.setName("колготки");
        category.setProducts(List.of(product));
        categories.add(category);
    }

    @Test
    public void registerUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepositoryMock.save(any(User.class))).thenReturn(new User());
        when(categoryServiceMock.read()).thenReturn(new ArrayList<>());
        assertEquals(PagesPathEnum.HOME_PAGE.getPath(), userService.registerUser(testUser, repPassword).getViewName());
    }

    @Test
    public void authenticate() {
        assertEquals(PagesPathEnum.SIGN_IN_PAGE.getPath(), userService.authenticate(false).getViewName());
    }

    @Test
    public void createUserOrder() throws NoOrderAddressException {
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
        doNothing().when(userServiceMock).update(isA(User.class));
        assertEquals(PagesPathEnum.HOME_PAGE.getPath(), orderService.createUserOrder(testUser, cart, "адрес").getViewName());
    }

    @Test
    public void addProductToCart() {
        when(productRepository.findById(anyInt())).thenReturn(product);
        Cart cart2 = (Cart) productService.addProductToCart(product.getId(), cart).getModelMap().get("cart");
        assertEquals(product, cart2.getProducts().get(cart.getProducts().size() - 1));
    }

    @Test
    public void deleteProductFromCart() {
        assertEquals(PagesPathEnum.EMPTY_CART_PAGE.getPath(), productService.deleteProductFromCart(product.getId(), cart).getViewName());
    }
    @Test
    public void loadUserByUsername(){
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(testUser);
        assertNotNull(customUserDetailsService.loadUserByUsername("email"));
    }

}
