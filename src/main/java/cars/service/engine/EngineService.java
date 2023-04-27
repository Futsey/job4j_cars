package cars.service.engine;

import cars.model.Engine;
import cars.store.engine.EngineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EngineService {

    private final EngineRepository store;

    public boolean add(Engine engine) {
        return store.add(engine);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public boolean update(Engine engine) {
        return store.update(engine);
    }

    public List<Engine> findAll() {
        return store.findAll();
    }

    public Optional<Engine> findById(int id) {
        return store.findById(id);
    }
}
