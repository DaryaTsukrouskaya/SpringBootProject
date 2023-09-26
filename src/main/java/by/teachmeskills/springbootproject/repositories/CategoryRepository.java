package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;

public interface CategoryRepository extends BaseRepository<Category> {
    Category findById(int id) throws DBConnectionException;
    void update(Category category);
}
