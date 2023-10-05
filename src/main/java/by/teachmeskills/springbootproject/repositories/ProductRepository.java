package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategoryId(int id);

    Page<Product> findByCategoryId(int id, Pageable page);

    Product findById(int id);

    Page<Product> findAllByCategoryIdAndNameContaining(int id, String name, Pageable pageable);
    @Query(value = "select p from Product p where p.price > :price")
    List<Product> findAllWithPriceGreaterThen(@Param("price")int price);

}
