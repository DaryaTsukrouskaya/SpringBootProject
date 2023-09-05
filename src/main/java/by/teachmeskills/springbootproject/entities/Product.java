package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Product extends BaseEntity {
    @NotNull
    @Size(min = 1, max = 60, message = "Пустое или длиннее 50 символов")
    private String name;
    @NotNull(message = "поле описание картинки продукта не должно быть пустым")
    private String description;
    @NotNull(message = "поле путь к картинке продукта не должно быть пустым")
    private String imagePath;
    @NotNull(message = "айди категории продукта не должно быть пустым")
    private int categoryId;
    @NotNull
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;
}