package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(int id);

    Page<Product> findByCategoryId(int id, Pageable page);

    Product findById(int id);
}
