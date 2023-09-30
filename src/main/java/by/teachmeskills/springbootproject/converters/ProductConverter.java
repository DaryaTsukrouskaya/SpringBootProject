package by.teachmeskills.springbootproject.converters;

import by.teachmeskills.springbootproject.csv.ProductCsv;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductConverter {
    private final CategoryRepository categoryRepository;

    public ProductConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Product convertFromCsv(ProductCsv productCsv) {
        return Optional.ofNullable(productCsv).map(p -> Product.builder().id(0).name(p.getName())
                .description(p.getDescription()).imagePath(p.getImagePath())
                .price(p.getPrice()).
                category(categoryRepository.findByName(p.getCategoryName())).build()).orElse(null);
    }

    public ProductCsv convertToCsv(Product product) {
        return Optional.ofNullable(product).map(p -> ProductCsv.builder().name(p.getName()).
                description(p.getDescription()).imagePath(p.getImagePath())
                .price(p.getPrice()).
                categoryName(p.getCategory().getName()).build()).orElse(null);
    }
}
