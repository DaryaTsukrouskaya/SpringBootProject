package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void create(Category category) {
        entityManager.persist(category);
    }

    @Override
    public void update(Category category) {
            entityManager.merge(category);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
    }

    @Override
    public List<Category> read() throws DBConnectionException {
        return entityManager.createQuery("select c from Category c ").getResultList();
    }

    @Override
    public Category findById(int id) throws DBConnectionException {
        return entityManager.find(Category.class, id);
    }
}
