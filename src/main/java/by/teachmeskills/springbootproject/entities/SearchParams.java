package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams {
    private String keyWords;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private String categoryName;


}
