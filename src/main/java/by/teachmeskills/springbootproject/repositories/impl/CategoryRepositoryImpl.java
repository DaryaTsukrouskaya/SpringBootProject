package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String CREATE_CATEGORY_QUERY = "INSERT INTO categories(name) VALUES(?)";
    private static final String DELETE_CATEGORY_QUERY = "DELETE FROM categories WHERE id=?";
    private static final String GET_ALL_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String GET_CATEGORY_QUERY = "SELECT * FROM categories WHERE id=?";

    @Autowired
    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Category category) throws DBConnectionException {
        jdbcTemplate.update(CREATE_CATEGORY_QUERY, category.getName());
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        jdbcTemplate.update(DELETE_CATEGORY_QUERY, id);
    }

    @Override
    public List<Category> read() throws DBConnectionException {
        return jdbcTemplate.query(GET_ALL_CATEGORIES_QUERY, (rs, rowNum) -> Category.builder().
                id(rs.getInt("id")).name(rs.getString("name"))
                .build());
    }

    @Override
    public Category findById(int id) throws DBConnectionException {
        return jdbcTemplate.queryForObject(GET_CATEGORY_QUERY, (RowMapper<Category>) (rs, rowNum) -> Category.builder().
                id(rs.getInt("id")).name(rs.getString("name"))
                .build(),id);
    }
}
