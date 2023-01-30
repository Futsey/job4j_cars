package cars.store;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import cars.model.User;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final CrudRepository crudRepository;
    private static final String SELECT_ALL_BY_ID = "FROM User ORDER by id ASC";
    private static final String SELECT_BY_ID = "FROM User WHERE id = :fId";
    private static final String SELECT_WHERE_LOGIN_LIKE = "FROM User WHERE login like = :fKey";
    private static final String SELECT_WHERE_LOGIN = "FROM User WHERE login = :fLogin";
    private static final String DELETE = "DELETE User WHERE id = :fId";

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query(SELECT_ALL_BY_ID, User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                SELECT_BY_ID, User.class,
                Map.of("fId", userId)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                SELECT_WHERE_LOGIN_LIKE, User.class,
                Map.of("fKey", key)
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.optional(
                SELECT_WHERE_LOGIN, User.class,
                Map.of("fLogin", login)
        );
    }
}
