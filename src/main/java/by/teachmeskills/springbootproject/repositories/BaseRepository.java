package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {

    void create(T entity) throws DBConnectionException;

    void delete(int id) throws DBConnectionException;

    List<T> read() throws DBConnectionException;

}

