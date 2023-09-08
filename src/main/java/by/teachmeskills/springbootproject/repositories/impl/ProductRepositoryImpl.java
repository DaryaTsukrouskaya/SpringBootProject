package by.teachmeskills.springbootproject.repositories.impl;


import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Product product) throws DBConnectionException {
        Session session = entityManager.unwrap(Session.class);
        session.persist(product);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
    }

    @Override
    public List<Product> read() throws DBConnectionException {
        return entityManager.createQuery("select p from Product p").getResultList();
    }

    @Override
    public Product findById(int id) throws DBConnectionException {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) throws DBConnectionException {
        return entityManager.createQuery("select p from Product p where p.category.id =: categoryId").setParameter("categoryId", categoryId).getResultList();
    }

    @Override
    public List<Product> findProductsByKeywords(String words) throws DBConnectionException {
        String searchParameter = "%" + words.trim() + "%";
        return entityManager.createQuery(" select p from Product p where p.name like: searchWords or p.description like: searchWords order by p.name asc").
                setParameter("searchWords", searchParameter).getResultList();

    }
}
