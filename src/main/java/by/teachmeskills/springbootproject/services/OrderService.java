package by.teachmeskills.springbootproject.services;


import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    Order findById(int id) throws DBConnectionException;

    List<Order> findByUserId(int id) throws DBConnectionException;
}
