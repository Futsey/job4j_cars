package cars.store.driver;

import cars.model.Driver;
import cars.store.CrudRepository;
import cars.store.car.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DriverRepository {


    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private static final String SELECT_ALL = """
            FROM Driver d
            JOIN FETCH d.user
            """;
    private static final String SELECT_BY_ID = """
            FROM Driver d
            JOIN FETCH d.user
            WHERE d.id = :fId
            """;
    private static final String DELETE = """
            DELETE Driver d
            WHERE d.id = :fId
            """;

    public DriverRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public boolean add(Driver driver) {
        boolean rsl = false;
        if (isCarPresent(driver)) {
            crudRepository.run(session -> session.save(driver));
            rsl = true;
        }
        return rsl;
    }

    public boolean isCarPresent(Driver driver) {
        return Optional.of(driver).isPresent();
    }

    public List<Driver> findAll() {
        return crudRepository.query(SELECT_ALL, Driver.class);
    }

    public boolean update(Driver driver) {
        boolean rsl = false;
        if (isCarPresent(driver)) {
            merge(driver);
            rsl = true;
        }
        return rsl;
    }

    public Driver merge(Driver driver) {
        crudRepository.run(tmpTask -> tmpTask.merge(driver));
        return Driver.builder()
                .name(driver.getName())
                .user(driver.getUser())
                .build();
    }

    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", id));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Exception: DriverRepository{ delete() }", e);
        }
        return rsl;
    }

    public Optional<Driver> findById(int id) {
        return crudRepository.optional(
                SELECT_BY_ID, Driver.class,
                Map.of("fId", id)
        );
    }
}
