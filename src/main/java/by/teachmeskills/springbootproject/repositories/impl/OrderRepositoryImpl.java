package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String CREATE_ORDER_QUERY = "INSERT INTO orders(price,orderDate,userId,address)";
    private static final String DELETE_ORDER_QUERY = "DELETE FROM orders WHERE id=?";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM orders";
    private static final String GET_ORDER_QUERY = "SELECT * FROM orders WHERE id=?";
    private static final String GET_ORDER_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE userId=?";

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Order order) throws DBConnectionException {
        jdbcTemplate.update(CREATE_ORDER_QUERY, order.getPrice(), Timestamp.valueOf(order.getOrderDate()),
                order.getUserId(), order.getAddress());
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        jdbcTemplate.update(DELETE_ORDER_QUERY, id);
    }

    @Override
    public List<Order> read() throws DBConnectionException {
        return jdbcTemplate.query(GET_ALL_ORDERS_QUERY, (rs, rowNum) -> Order.builder().id(rs.getInt("id")).
                price(rs.getBigDecimal("price")).
                orderDate(rs.getTimestamp("orderDate").toLocalDateTime()).userId(rs.getInt("userId")).
                address(rs.getString("address")).build());
    }

    @Override
    public Order findById(int id) throws DBConnectionException {
        return jdbcTemplate.queryForObject(GET_ORDER_QUERY, (RowMapper<Order>) (rs, rowNum) -> Order.builder().id(rs.getInt("id")).
                price(rs.getBigDecimal("price")).
                orderDate(rs.getTimestamp("orderDate").toLocalDateTime()).userId(rs.getInt("userId")).
                address(rs.getString("address")).build(), id);
    }

    @Override
    public List<Order> findByUserId(int id) throws DBConnectionException {
        return jdbcTemplate.query(GET_ORDER_BY_USER_ID_QUERY, (RowMapper<Order>) (rs, rowNum) -> Order.builder().id(rs.getInt("id")).
                price(rs.getBigDecimal("price")).
                orderDate(rs.getTimestamp("orderDate").toLocalDateTime()).userId(rs.getInt("userId")).
                address(rs.getString("address")).build(), id);
    }
}
