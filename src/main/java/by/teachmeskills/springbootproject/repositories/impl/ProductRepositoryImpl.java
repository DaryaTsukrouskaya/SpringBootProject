package by.teachmeskills.springbootproject.repositories.impl;


import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String CREATE_PRODUCT_QUERY = "INSERT INTO products(name,description,imagePath,categoryId,price) VALUES(?,?,?,?,?)";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id=?";
    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products";
    private static final String GET_PRODUCT_QUERY = "SELECT * FROM products WHERE id=?";
    private static final String GET_CATEGORY_PRODUCTS_QUERY = "SELECT * FROM products WHERE categoryId=?";
    private static final String GET_PRODUCTS_BY_KEYWORDS = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? ORDER BY name ASC";

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Product product) throws DBConnectionException {
        jdbcTemplate.update(CREATE_PRODUCT_QUERY, product.getName(), product.getDescription(),
                product.getImagePath(), product.getCategoryId(), product.getPrice());

    }

    @Override
    public void delete(int id) throws DBConnectionException {
        jdbcTemplate.update(DELETE_PRODUCT_QUERY, id);

    }

    @Override
    public List<Product> read() throws DBConnectionException {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_QUERY, (rs, rowNum) -> Product.builder().id(rs.getInt("id")).
                name(rs.getString("name")).description(rs.getString("description")).
                imagePath(rs.getString("imagePath")).categoryId(rs.getInt("categoryId")).price(rs.getBigDecimal("price"))
                .build());
    }

    @Override
    public Product findById(int id) throws DBConnectionException {
        return jdbcTemplate.queryForObject(GET_PRODUCT_QUERY, (RowMapper<Product>) (rs, rowNum) -> Product.builder().id(rs.getInt("id")).
                name(rs.getString("name")).description(rs.getString("description")).
                imagePath(rs.getString("imagePath")).categoryId(rs.getInt("categoryId")).price(rs.getBigDecimal("price"))
                .build(), id);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) throws DBConnectionException {
        return jdbcTemplate.query(GET_CATEGORY_PRODUCTS_QUERY, (rs, rowNum) -> Product.builder().id(rs.getInt("id")).
                name(rs.getString("name")).description(rs.getString("description")).
                imagePath(rs.getString("imagePath")).categoryId(rs.getInt("categoryId")).price(rs.getBigDecimal("price"))
                .build(), categoryId);
    }

    @Override
    public List<Product> findProductsByKeywords(String words) throws DBConnectionException {
        return jdbcTemplate.query(GET_PRODUCTS_BY_KEYWORDS, (rs, rowNum) -> Product.builder().id(rs.getInt("id")).
                name(rs.getString("name")).description(rs.getString("description")).
                imagePath(rs.getString("imagePath")).categoryId(rs.getInt("categoryId")).price(rs.getBigDecimal("price"))
                .build(), "%" + words + "%", "%" + words + "%");
    }
}
