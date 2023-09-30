package by.teachmeskills.springbootproject.csv;

import by.teachmeskills.springbootproject.entities.Category;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCsv {
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String description;
    @CsvBindByName
    private String imagePath;
    @CsvBindByName
    private String categoryName;
    @CsvBindByName
    private BigDecimal price;
}
