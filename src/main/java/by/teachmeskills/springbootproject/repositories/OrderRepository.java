package by.teachmeskills.springbootproject.repositories;


import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(int id);

    Page<Order> findByUserId(int id, Pageable page);
}
