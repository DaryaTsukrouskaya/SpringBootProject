package by.teachmeskills.springbootproject.controllers;


import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.services.impl.CategoryServiceImpl;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryServiceImpl categoryService;

    @Autowired
    public HomeController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ModelAndView getHomePage() throws DBConnectionException {
        return categoryService.getCategoriesData();
    }

    @GetMapping("/loadCsvFile")
    public void loadToFile(HttpServletResponse servletResponse) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, DBConnectionException, IOException {
        categoryService.saveCategoriesToFile(servletResponse);
    }

    @PostMapping("/loadFromFile")
    public ModelAndView loadFromFile(@RequestParam("file") MultipartFile file) throws DBConnectionException {
        return categoryService.saveCategoriesFromFile(file);
    }
}
