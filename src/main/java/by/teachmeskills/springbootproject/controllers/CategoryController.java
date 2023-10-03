package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/category")
@SessionAttributes({"paginationParams"})
public class CategoryController {
    private final ProductService productService;

    @Autowired
    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView getCategoryProductsPage(@PathVariable int id, @ModelAttribute("paginationParams") PaginationParams pagination) {
        pagination.setPageNumber(0);
        pagination.setPageSize(1);
        ModelAndView modelAndView = productService.getProductsByCategory(id, pagination);
        return modelAndView;
    }

    @GetMapping("/pagination/{id}/{pageNumber}")
    public ModelAndView getCategoryProductsPaginated(@PathVariable int id, @PathVariable int pageNumber, @SessionAttribute("paginationParams") PaginationParams params) {
        params.setPageNumber(pageNumber);
        ModelAndView modelAndView = productService.getProductsByCategory(id, params);
        return modelAndView;
    }

    @PostMapping("/loadCsvFile/{id}")
    public void loadToFile(HttpServletResponse servletResponse, @PathVariable int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, DBConnectionException, IOException {
        productService.saveCategoryProductsToFile(servletResponse, id);
    }

    @PostMapping("/loadFromFile/{id}")
    public ModelAndView loadFromFile(@RequestParam("file") MultipartFile file, @PathVariable int id) throws DBConnectionException {
        return productService.saveProductsFromFile(file, id);
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }
}
