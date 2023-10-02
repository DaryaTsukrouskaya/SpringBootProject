package by.teachmeskills.springbootproject.services;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface ProductService extends BaseService<Product> {
    Product findById(int id);

    ModelAndView getProductsByCategory(int id, int pageNumber, int pageSize);

    ModelAndView addProductToCart(int id, Cart cart);

    ModelAndView deleteProductFromCart(int id, Cart cart);

    ModelAndView findProductByIdForProductPage(int id);

    ModelAndView clearCart(Cart cart);

    void saveCategoryProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    ModelAndView saveProductsFromFile(MultipartFile file, int id);
}
