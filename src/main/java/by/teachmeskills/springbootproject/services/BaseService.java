package by.teachmeskills.springbootproject.services;



import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    List<T> read() throws DBConnectionException;

    void create(T entity) throws DBConnectionException, UserAlreadyExistsException;

    void delete(int id) throws DBConnectionException;
}
