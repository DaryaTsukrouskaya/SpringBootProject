package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.converters.ProductConverter;
import by.teachmeskills.springbootproject.csv.ProductCsv;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.enums.PagesPathEnum;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.ProductSearchSpecification;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.categoryService = categoryService;
    }


    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public void create(Product product) {
        productRepository.save(product);

    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public ModelAndView getProductsByCategory(int id, PaginationParams params) {
        if (params.getPageNumber() < 0) {
            params.setPageNumber(0);
        }
        ModelMap modelMap = new ModelMap();
        Category category = categoryService.findById(id);
        Pageable pageable = PageRequest.of(params.getPageNumber(), params.getPageSize(), Sort.by("name").ascending());
        category.setProducts(productRepository.findByCategoryId(id, pageable).getContent());
        if (category.getProducts().isEmpty()) {
            params.setPageNumber(params.getPageNumber() - 1);
            pageable = PageRequest.of(params.getPageNumber(), params.getPageSize(), Sort.by("name").ascending());
            category.setProducts(productRepository.findByCategoryId(id, pageable).getContent());
        }
        modelMap.addAttribute("category", category);
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView addProductToCart(int productId, Cart cart) {
        ModelMap modelMap = new ModelMap();
        Product product = findById(productId);
        cart.addProduct(product);
        modelMap.addAttribute("cart", cart);
        modelMap.addAttribute("product", product);
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView deleteProductFromCart(int productId, Cart cart) {
        ModelMap modelMap = new ModelMap();
        cart.removeProduct(productId);
        modelMap.addAttribute("cart", cart);
        if (cart.getProducts().isEmpty()) {
            return new ModelAndView(PagesPathEnum.EMPTY_CART_PAGE.getPath());
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView clearCart(Cart cart) {
        cart.clear();
        ModelMap modelMap = new ModelMap();
        if (cart.getProducts().isEmpty()) {
            return new ModelAndView(PagesPathEnum.EMPTY_CART_PAGE.getPath());
        }
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelMap.addAttribute("cart", cart));
    }


    @Override
    public ModelAndView findProductByIdForProductPage(int id) {
        ModelMap modelMap = new ModelMap();
        Product product = findById(id);
        modelMap.addAttribute("categoryName", product.getName());
        modelMap.addAttribute("product", product);
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView saveProductsFromFile(MultipartFile file, int id) {
        List<ProductCsv> csvProducts = parseCsv(file);
        ModelMap modelMap = new ModelMap();
        List<Product> products = csvProducts.stream().map(productConverter::convertFromCsv).toList();
        products.stream().forEach(c -> c.setCategory(categoryService.findById(id)));
        for (Product product : products) {
            productRepository.save(product);
        }
        Pageable pageable = PageRequest.of(0, 0, Sort.by("name").ascending());
        Category category = categoryService.findById(id);
        category.setProducts(productRepository.findByCategoryId(id, pageable).getContent());
        modelMap.addAttribute("category", category);
        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), modelMap);
    }

    private List<ProductCsv> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(reader).withType(ProductCsv.class).
                        withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
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
    public void saveCategoryProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Product> products = productRepository.findByCategoryId(id);
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<ProductCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsv>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            beanToCsv.write(products.stream().map(productConverter::convertToCsv).toList());
        }
    }

    @Override
    public ModelAndView searchProducts(SearchParams searchParams, PaginationParams paginationParams) {
        if (paginationParams.getPageNumber() < 0) {
            paginationParams.setPageNumber(0);
        }
        ProductSearchSpecification specification = new ProductSearchSpecification(searchParams);
        Pageable pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("name").ascending());
        ModelMap modelMap = new ModelMap();
        List<Product> products = productRepository.findAll(specification, pageable).getContent();
        if (products.isEmpty()) {
            paginationParams.setPageNumber(paginationParams.getPageNumber() - 1);
            pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("name").ascending());
            products = productRepository.findAll(specification, pageable).getContent();
        }
        modelMap.addAttribute("products", products);
        return new ModelAndView(PagesPathEnum.SEARCH_PAGE.getPath(), modelMap);
    }
}
