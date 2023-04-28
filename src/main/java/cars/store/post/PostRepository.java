package cars.store.post;

import cars.model.Car;
import cars.model.Post;
import cars.store.CrudRepository;
import cars.store.car.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class.getName());

    private static final String SELECT_ALL = """
            FROM Post p
            JOIN FETCH p.user
            JOIN FETCH p.car
            """;

    private static final String SELECT_BY_24H = """
            FROM Post p
            WHERE p.created 
            BETWEEN :fDayBefore 
            AND :fNow
            """;

    private static final String SELECT_WITH_FILE = """
            FROM Post p
            WHERE p.fileId != NULL
            """;

    private static final String SELECT_BY_CAR_NAME = """
            FROM Post p
            JOIN FETCH p.car
            WHERE p.car = :fcarId
            """;

    public PostRepository(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public List<Post> findAll() {
        return crudRepository.query(SELECT_ALL, Post.class);
    }

    public List<Post> findBy24H() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayBefore = LocalDateTime.now().minusDays(1);
        return crudRepository.query(SELECT_BY_24H, Post.class,
                Map.of("fNow", now, "fDayBefore", dayBefore));
    }

    public List<Post> findByWithPhoto() {
        return crudRepository.query(SELECT_WITH_FILE, Post.class);
    }

    public List<Post> findByCarName(Car car) {
        return crudRepository.query(SELECT_BY_CAR_NAME,
                Post.class,
                Map.of("fcarId", car.getId()));
    }
}
