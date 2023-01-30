package cars.service;

import cars.model.User;
import cars.store.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository store;

    public UserService(UserRepository store) {
        this.store = store;
    }

    public User add(User user) {
        return store.create(user);
    }

    public void delete(int userId) {
        store.delete(userId);
    }

    public void update(User user) {
        store.update(user);
    }

    public List<User> findAllOrderById() {
        return store.findAllOrderById();
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public List<User> findByLikeLogin(String key) {
        return store.findByLikeLogin(key);
    }

    public Optional<User> findByLogin(String name) {
        return store.findByLogin(name);
    }
}
