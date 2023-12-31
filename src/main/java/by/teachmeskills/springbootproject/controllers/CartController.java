package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.NoOrderAddressException;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.impl.OrderServiceImpl;
import by.teachmeskills.springbootproject.services.impl.ProductServiceImpl;
import by.teachmeskills.springbootproject.services.impl.UserServiceImpl;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/cart")
@SessionAttributes({"cart"})
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    public CartController(ProductServiceImpl productService, UserServiceImpl userService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public ModelAndView openCartPage(@ModelAttribute("cart") Cart cart) {
        if (cart.getProducts().isEmpty()) {
            return new ModelAndView(PagesPathEnum.EMPTY_CART_PAGE.getPath());
        }
        ModelMap modelMap = new ModelMap();
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap.addAttribute("cart", cart));
    }

    @GetMapping("/add")
    public ModelAndView addProduct(@RequestParam("product_id") int id, @ModelAttribute("cart") Cart cart) {
        return productService.addProductToCart(id, cart);

    }

    @GetMapping("/delete")
    public ModelAndView deleteProductFromCart(@ModelAttribute("cart") Cart cart, @RequestParam("product_id") int id) {
        return productService.deleteProductFromCart(id, cart);
    }

    @GetMapping("/clear")
    public ModelAndView clearCart(@ModelAttribute("cart") Cart cart) {
        return productService.clearCart(cart);
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(@ModelAttribute("cart") Cart cart) {
        return userService.checkout(cart);
    }

    @PostMapping("/createOrder")
    public ModelAndView buy(@ModelAttribute("cart") Cart cart, @RequestParam("address") String address) throws NoOrderAddressException {
        return orderService.createUserOrder(userService.getCurrentUser(), cart, address);
    }

    @ModelAttribute("cart")
    public Cart setShoppingCart() {
        return new Cart();
    }
}
