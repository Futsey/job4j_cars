package cars.service.user;

import cars.dto.file.FileDto;
import cars.model.User;
import cars.service.file.FileService;
import cars.store.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository store;
    private final FileService fileService;

    public UserService(UserRepository store, FileService fileService) {
        this.store = store;
        this.fileService = fileService;
    }

    public Optional<User> add(User user,  FileDto image) {
        user.setCreated(LocalDateTime.now());
        saveNewFile(user, image);
        return store.add(user);
    }

    private void saveNewFile(User user, FileDto image) {
        var file = fileService.save(image);
        user.setFileId(file.getId());
    }

    public boolean delete(int userId) {
        var fileOptional = findById(userId);
        if (fileOptional.isEmpty()) {
            return false;
        }
        fileService.deleteById(fileOptional.get().getFileId());
        return store.delete(userId);
    }

    public boolean update(User user, FileDto image) {
        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return store.update(user);
        }
        /* если передан новый не пустой файл, то старый удаляем, а новый сохраняем */
        var oldFileId = user.getFileId();
        saveNewFile(user, image);
        var isUpdated = store.update(user);
        fileService.deleteById(oldFileId);
        return isUpdated;
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
