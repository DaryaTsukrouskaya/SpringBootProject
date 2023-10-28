package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ModelAndView getProductPage(@PathVariable int id) {
        return productService.findProductByIdForProductPage(id);
    }
}
