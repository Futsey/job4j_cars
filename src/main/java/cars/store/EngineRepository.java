package cars.store;

import cars.model.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EngineRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private static final String SELECT_ALL = """
            SELECT FROM Engine
            """;
    private static final String SELECT_BY_ID = """
            FROM Engine e
            WHERE e.id = :fId
            """;
    private static final String DELETE = """
            DELETE Engine e
            WHERE e.id = :fId
            """;

    public EngineRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public boolean add(Engine engine) {
        boolean rsl = false;
        if (isCarPresent(engine)) {
            crudRepository.run(session -> session.save(engine));
            rsl = true;
        }
        return rsl;
    }

    public boolean isCarPresent(Engine engine) {
        return Optional.of(engine).isPresent();
    }

    public List<Engine> findAll() {
        return crudRepository.query(SELECT_ALL, Engine.class);
    }

    public boolean update(Engine engine) {
        boolean rsl = false;
        if (isCarPresent(engine)) {
            merge(engine);
            rsl = true;
        }
        return rsl;
    }

    public Engine merge(Engine engine) {
        crudRepository.run(tmpTask -> tmpTask.merge(engine));
        return Engine.builder()
                .name(engine.getName())
                .build();
    }

    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", id));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Exception: EngineRepository{ delete() }", e);
        }
        return rsl;
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                SELECT_BY_ID, Engine.class,
                Map.of("fId", id)
        );
    }
}
