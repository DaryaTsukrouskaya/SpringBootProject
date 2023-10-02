package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    User findById(int id);
}
