package cars.store.user;

import cars.store.CrudRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import cars.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class.getName());
    private static final String SELECT_ALL_ASC = """
    FROM User u
    ORDER by u.id
    ASC
    """;
    private static final String SELECT_BY_ID = """
    FROM User u
    WHERE u.id = :fId
    """;
    private static final String SELECT_WHERE_LOGIN_LIKE = """
    FROM User u
    WHERE u.login
    LIKE = :fKey
    """;
    private static final String SELECT_WHERE_LOGIN_AND_PASSWORD = """
    FROM User u
    WHERE u.login = :flogin
    AND u.password = :fpassword
    """;
    private static final String DELETE = """
    DELETE User u
    WHERE u.id = :fId
    """;

    public Optional<User> add(User user) {
        Optional<User> nonNullUser = Optional.empty();
        try {
            crudRepository.run(session -> session.save(user));
            nonNullUser = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Exception: UserRepository{ add() }", e);
        }
        return nonNullUser;
    }

    public List<User> findAllASC() {
        return crudRepository.query(SELECT_ALL_ASC, User.class);
    }

    public boolean update(User user) {
        boolean rsl = false;
        if (isUserPresent(user)) {
            merge(user);
            rsl = true;
        }
        return rsl;
    }

    public boolean isUserPresent(User user) {
        return Optional.of(user).isPresent();
    }

    public User merge(User user) {
        crudRepository.run(tmpTask -> tmpTask.merge(user));
        return User.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .fileId(user.getFileId())
                .build();
    }

    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", id));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Exception: UserRepository{ update() }", e);
        }
        return rsl;
    }

    public Optional<User> findByLogin(String key) {
        return crudRepository.optional(
                SELECT_WHERE_LOGIN_LIKE, User.class,
                Map.of("fkey", key)
        );
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Optional<User> userDB = Optional.empty();
        try {
            userDB = crudRepository.optional(
                    SELECT_WHERE_LOGIN_AND_PASSWORD,
                    User.class,
                    Map.of("flogin", login, "fpassword", password));
        } catch (Exception e) {
            LOG.error("Exception: UserRepository{ findByLoginAnaPassword() }", e);
        }
        return userDB;
    }

    public Optional<User> findById(int id) {
        return crudRepository.optional(
                SELECT_BY_ID, User.class,
                Map.of("fId", id)
        );
    }
}
