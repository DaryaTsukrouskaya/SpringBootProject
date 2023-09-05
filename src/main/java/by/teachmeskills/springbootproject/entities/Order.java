package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Order extends BaseEntity {
    @NotNull
    @Digits(integer = 6, fraction = 3, message = "не соответствует формату цены")
    private BigDecimal price;
    @Past(message = "дата еще не наступила")
    @NotNull(message = "дата заказа не должна быть пустой")
    private LocalDateTime orderDate;
    @NotNull(message = "айди пользователя заказа не должно быть пустым")
    private int userId;
    @NotNull(message = "дата заказа не должна быть пустой")
    private String address;
    private List<Product> products;

    public Order(int id, LocalDateTime orderDate, List<Product> products) {
        this.id = id;
        this.orderDate = orderDate;
        this.products = products;
    }
}
