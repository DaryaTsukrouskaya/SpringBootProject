package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.impl.ProductServiceImpl;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final ProductService productService;

    @Autowired
    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView getCategoryProductsPage(@PathVariable int id) throws DBConnectionException {
        return productService.getProductsByCategory(id);
    }

    @GetMapping("/loadCsvFile")
    public void loadToFile(HttpServletResponse servletResponse, @PathVariable int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, DBConnectionException, IOException {
        productService.saveCategoryProductsToFile(servletResponse);
    }

    @PostMapping("/loadFromFile/{id}")
    public ModelAndView loadFromFile(@RequestParam("file") MultipartFile file, @PathVariable int id) throws DBConnectionException {
        return productService.saveProductsFromFile(file, id);
    }
}
