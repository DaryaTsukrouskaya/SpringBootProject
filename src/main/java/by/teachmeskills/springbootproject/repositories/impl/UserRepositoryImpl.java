package by.teachmeskills.springbootproject.repositories.impl;


import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void create(User user) throws DBConnectionException, UserAlreadyExistsException {
        Session session = entityManager.unwrap(Session.class);
        session.persist(user);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        Session session = entityManager.unwrap(Session.class);
        User user = session.find(User.class, id);
        session.remove(user);
    }

    @Override
    public List<User> read() throws DBConnectionException {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User findById(int id) throws DBConnectionException {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws DBConnectionException {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("select u from User u where u.email =: email and u.password =: password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        return (User) query.uniqueResult();
    }

    @Override
    public void updatePassword(String password, String email) throws DBConnectionException {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("update User u set u.password=:password where u.email=: email");
        query.setParameter("password", password);
        query.setParameter("email", email);
        query.executeUpdate();

    }

    @Override
    public void update(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(user);
    }

    @Override
    public void updateEmail(String previousEmail, String newEmail) throws DBConnectionException {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("update User u set u.email=:newEmail where u.email=: previousEmail");
        query.setParameter("newEmail", newEmail);
        query.setParameter("previousEmail", previousEmail);
        query.executeUpdate();
    }
}