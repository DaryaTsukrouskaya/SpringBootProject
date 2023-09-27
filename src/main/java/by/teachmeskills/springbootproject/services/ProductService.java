package by.teachmeskills.springbootproject.services;


import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface ProductService extends BaseService<Product> {
    Product findById(int id) throws DBConnectionException;

    ModelAndView getProductsByCategory(int id) throws DBConnectionException;

    ModelAndView addProductToCart(int id, Cart cart) throws DBConnectionException;

    ModelAndView deleteProductFromCart(int id, Cart cart);

    ModelAndView findProductByIdForProductPage(int id) throws DBConnectionException;

    ModelAndView clearCart(Cart cart);

    public ModelAndView searchProductsPaged(int pageNumber, String keyWords) throws DBConnectionException;

    void saveCategoryProductsToFile(HttpServletResponse servletResponse) throws DBConnectionException, IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    ModelAndView saveProductsFromFile(MultipartFile file, int id) throws DBConnectionException;
}
