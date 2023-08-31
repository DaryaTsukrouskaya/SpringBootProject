package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    @NotNull
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        Optional<Product> product = products.stream().filter(p -> p.getId() == id).findFirst();
        product.ifPresent(p -> products.remove(p));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void clear() {
        products.clear();
    }
}
