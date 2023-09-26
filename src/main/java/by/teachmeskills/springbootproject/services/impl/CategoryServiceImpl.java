package by.teachmeskills.springbootproject.services.impl;


import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.impl.CategoryRepositoryImpl;
import by.teachmeskills.springbootproject.services.CategoryService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepositoryImpl categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> read() throws DBConnectionException {
        return categoryRepository.read();
    }

    @Override
    public void create(Category category) throws DBConnectionException {
        categoryRepository.create(category);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        categoryRepository.delete(id);
    }


    public Category findById(int id) throws DBConnectionException {
        return categoryRepository.findById(id);
    }

    public ModelAndView getCategoriesData() throws DBConnectionException {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("categories", categoryRepository.read());
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);

    }

    @Override
    public ModelAndView saveCategoriesFromFile(MultipartFile file) throws DBConnectionException {
        List<Category> csvCategories = parseCsv(file);
        ModelMap modelMap = new ModelMap();
        if (Optional.ofNullable(csvCategories).isPresent()) {
            for (Category csvCategory : csvCategories) {
                categoryRepository.create(csvCategory);
            }
            modelMap.addAttribute("categories", categoryRepository.read());
        }
        return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), modelMap);
    }

    private List<Category> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<Category> csvToBean = new CsvToBeanBuilder<Category>(reader).withType(Category.class).
                        withIgnoreLeadingWhiteSpace(true).withSeparator(',').build();
                return csvToBean.parse();
            } catch (IOException e) {
                log.error("Exception occurred during csv parsing:" + e.getMessage());
            }
        } else {
            log.error("Empty scv file is uploaded");
        }
        return Collections.emptyList();
    }

    @Override
    public void saveCategoriesToFile(HttpServletResponse servletResponse) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, DBConnectionException {
        List<Category> categories = categoryRepository.read();
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<Category> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Category>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "categories.csv");
            statefulBeanToCsv.write(categories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
