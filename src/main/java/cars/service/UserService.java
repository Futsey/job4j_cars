package cars.service;

import cars.model.User;
import cars.store.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository store;

    public UserService(UserRepository store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        user.setCreated(LocalDateTime.now());
        return store.add(user);
    }

    public boolean delete(int userId) {
        return store.delete(userId);
    }

    public boolean update(int userId) {
        return store.update(userId);
    }

    public List<User> findAllOrderASCById() {
        return store.findAllASC();
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public Optional<User> findByLogin(String key) {
        return store.findByLogin(key);
    }

    public Optional<User> findByLoginAndPass(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }
}
