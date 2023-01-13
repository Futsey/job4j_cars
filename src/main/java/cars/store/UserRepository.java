package cars.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;
import cars.model.User;

@AllArgsConstructor
public class UserRepository {

    private final SessionFactory sf;
    private static final String UPDATE = "UPDATE User SET login = :fLogin, password = :fNPassword WHERE id = :fId";
    private static final String DELETE = "DELETE User WHERE id = :fId";
    private static final String ORDER_BY_ID = "FROM User ORDER BY id";
    private static final String FIND_BY_LOGIN_LIKE = "FROM User WHERE login LIKE :key";
    private static final String FIND_BY_LOGIN = "FROM User WHERE login= :login";

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(UPDATE)
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fLogin", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(DELETE)
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
        }
    }

    /**
     * Список пользователей отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        List<User> sortedByIDList = session.createQuery(ORDER_BY_ID, User.class).list();
        session.close();
        return sortedByIDList;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Optional<User> nonNullUser = Optional.ofNullable(session.find(User.class, id));
        session.close();
        return nonNullUser;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        List<User> rsl = session.createQuery(FIND_BY_LOGIN_LIKE, User.class)
                .setParameter("key", "%" + key + "%")
                .list();
        session.close();
        return rsl;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Optional<User> rsl = session.createQuery(FIND_BY_LOGIN, User.class)
                .setParameter("login", login)
                .uniqueResultOptional();
        session.close();
        return rsl;
    }
}
