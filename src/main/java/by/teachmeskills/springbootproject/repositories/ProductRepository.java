package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    Product findById(int id) throws DBConnectionException;

    List<Product> getProductsByCategory(int id) throws DBConnectionException;

    List<Product> findProductsByKeywords(String words) throws DBConnectionException;

}
