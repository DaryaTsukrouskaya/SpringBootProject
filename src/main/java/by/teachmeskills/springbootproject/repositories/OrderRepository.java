package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order findById(int id) throws DBConnectionException;

    List<Order> findByUserId(int id) throws DBConnectionException;
}
