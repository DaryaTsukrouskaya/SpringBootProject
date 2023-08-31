package by.teachmeskills.springbootproject.services.impl;


import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.impl.OrderRepositoryImpl;
import by.teachmeskills.springbootproject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepositoryImpl orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> read() throws DBConnectionException {
        return orderRepository.read();
    }

    @Override
    public void create(Order order) throws DBConnectionException, UserAlreadyExistsException {
        orderRepository.create(order);
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        orderRepository.delete(id);
    }

    @Override
    public Order findById(int id) throws DBConnectionException {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(int id) throws DBConnectionException {
        return orderRepository.findByUserId(id);
    }
}
