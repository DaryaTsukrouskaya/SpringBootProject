package by.teachmeskills.springbootproject.repositories.impl;


import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.DBConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static String CREATE_USER_QUERY = "INSERT INTO users(name,surname,birthDate,email,password) VALUES(?,?,?,?,?)";
    private final static String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
    private final static String GET_USER_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM users WHERE email=? AND password=?";
    private final static String DELETE_USER_QUERY = "DELETE FROM users WHERE id=?";
    private final static String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private final static String UPDATE_USER_PASSWORD_QUERY = "UPDATE users SET password=? WHERE email=?";
    private final static String UPDATE_USER_EMAIL_QUERY = "UPDATE users SET email=? WHERE email=?";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) throws DBConnectionException, UserAlreadyExistsException {
        try {
            if (findByEmailAndPassword(user.getEmail(), user.getPassword()) != null) {
                throw new UserAlreadyExistsException("Такой пользователь уже существует!");
            }
        } catch (EmptyResultDataAccessException ex) {
            jdbcTemplate.update(CREATE_USER_QUERY, user.getName(), user.getSurname(),
                    Timestamp.valueOf(user.getBirthDate().atStartOfDay()), user.getEmail(), user.getPassword());
        }
    }

    @Override
    public void delete(int id) throws DBConnectionException {
        jdbcTemplate.update(DELETE_USER_QUERY, id);
    }

    @Override
    public List<User> read() throws DBConnectionException {
        return jdbcTemplate.query(GET_ALL_USERS_QUERY, (rs, rowNum) -> User.builder()
                .id(rs.getInt("id")).name(rs.getString("name")).surname(rs.getString("surname")).
                birthDate(rs.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).email(rs.getString("email"))
                .password(rs.getString("password")).build());
    }

    @Override
    public User findById(int id) throws DBConnectionException {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id")).name(rs.getString("name")).surname(rs.getString("surname")).
                birthDate(rs.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).email(rs.getString("email"))
                .password(rs.getString("password")).build(), id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws DBConnectionException {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_AND_PASSWORD_QUERY, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id")).name(rs.getString("name")).surname(rs.getString("surname")).
                birthDate(rs.getTimestamp("birthDate").toLocalDateTime().toLocalDate()).email(rs.getString("email"))
                .password(rs.getString("password")).build(), email, password);
    }

    @Override
    public void updatePassword(String password, String email) throws DBConnectionException {
        jdbcTemplate.update(UPDATE_USER_PASSWORD_QUERY, password, email);

    }

    @Override
    public void updateEmail(String previousEmail, String newEmail) throws DBConnectionException {
        jdbcTemplate.update(UPDATE_USER_EMAIL_QUERY, newEmail, previousEmail);
    }
}