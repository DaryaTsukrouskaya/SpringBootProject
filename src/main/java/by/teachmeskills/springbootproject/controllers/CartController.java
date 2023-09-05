package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.impl.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    public CartController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView openCartPage(@ModelAttribute("cart") Cart cart) {
        ModelMap modelMap = new ModelMap();
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap.addAttribute("cart", cart));
    }

    @GetMapping("/add")
    public ModelAndView addProduct(@RequestParam("product_id") int id, @ModelAttribute("cart") Cart cart) throws DBConnectionException {
        return productService.addProductToCart(id, cart);

    }

    @GetMapping("/delete")
    public ModelAndView deleteProductFromCart(@ModelAttribute("cart") Cart cart, @RequestParam("product_id") int id) {
        return productService.deleteProductFromCart(id, cart);
    }

    @GetMapping("/clear")
    public ModelAndView clearCart(@ModelAttribute("cart") Cart cart) {
        cart.clear();
        ModelMap modelMap = new ModelMap();
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap.addAttribute("cart", cart));
    }

    @ModelAttribute("cart")
    public Cart setShoppingCart() {
        return new Cart();
    }
}
