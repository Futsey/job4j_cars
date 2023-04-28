package cars.store.car;

import cars.model.Car;
import cars.store.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CarRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private static final String SELECT_ALL = """
            FROM Car c
            JOIN FETCH c.engine
            JOIN FETCH c.owners
            """;
    private static final String SELECT_BY_ID = """
            FROM Car c
            JOIN FETCH c.engine
            JOIN FETCH c.owners
            WHERE c.id = :fId
            """;
    private static final String DELETE = """
            DELETE Car c
            WHERE c.id = :fId
            """;

    public CarRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public boolean add(Car car) {
        boolean rsl = false;
        if (isCarPresent(car)) {
            crudRepository.run(session -> session.save(car));
            rsl = true;
        }
        return rsl;
    }

    public boolean isCarPresent(Car car) {
        return Optional.of(car).isPresent();
    }

    public List<Car> findAll() {
        return crudRepository.query(SELECT_ALL, Car.class);
    }

    public boolean update(Car car) {
        boolean rsl = false;
        if (isCarPresent(car)) {
            merge(car);
            rsl = true;
        }
        return rsl;
    }

    public Car merge(Car car) {
        crudRepository.run(tmpTask -> tmpTask.merge(car));
        return Car.builder()
                .name(car.getName())
                .engine(car.getEngine())
                .owners(car.getOwners())
                .build();
    }

    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", id));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Exception: PostRepository{ delete() }", e);
        }
        return rsl;
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                SELECT_BY_ID, Car.class,
                Map.of("fId", id)
        );
    }
}
