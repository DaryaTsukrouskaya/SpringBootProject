package by.teachmeskills.springbootproject.repositories;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;


public interface UserRepository extends BaseRepository<User> {
    User findById(int id) throws DBConnectionException;

    User findByEmailAndPassword(String email, String password) throws DBConnectionException;

    void updatePassword(String password, String email) throws DBConnectionException;

    void updateEmail(String previousEmail, String newEmail) throws DBConnectionException;
}
