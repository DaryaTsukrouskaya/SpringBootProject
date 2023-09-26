package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseService<Category> {
    Category findById(int id) throws DBConnectionException;

    ModelAndView saveCategoriesFromFile(MultipartFile file) throws DBConnectionException;

    void saveCategoriesToFile(HttpServletResponse servletResponse) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, DBConnectionException;
}
