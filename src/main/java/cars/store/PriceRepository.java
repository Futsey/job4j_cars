package cars.store;

import cars.model.PriceHistory;
import cars.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriceRepository {

    private final CrudRepository crudRepository;
    private static final String SELECT_ALL_BY_ID = "FROM PriceHistory ORDER by id ASC";
    private static final String SELECT_BY_ID = "FROM PriceHistory WHERE id = :fId";
    private static final String DELETE = "DELETE PriceHistory WHERE id = :fId";

    /**
     * Сохранить в базе.
     * @param priceHistory история сделок.
     * @return история сделки.
     */
    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.persist(priceHistory));
        return priceHistory;
    }

    /**
     * Обновить в базе историю сделок.
     * @param priceHistory история сделок.
     */
    public void update(PriceHistory priceHistory) {
        crudRepository.run(session -> session.merge(priceHistory));
    }

    /**
     * Удалить историю сделки по id.
     * @param priceHistoryId ID истории сделок
     */
    public void delete(int priceHistoryId) {
        crudRepository.run(
                DELETE,
                Map.of("fId", priceHistoryId)
        );
    }

    /**
     * Список историй сделок отсортированных по id.
     * @return список историй сделок.
     */
    public List<PriceHistory> findAllOrderById() {
        return crudRepository.query(SELECT_ALL_BY_ID, PriceHistory.class);
    }

    /**
     * Найти историю сделки по ID
     * @return пользователь.
     */
    public Optional<PriceHistory> findById(int priceHistoryId) {
        return crudRepository.optional(
                SELECT_BY_ID, PriceHistory.class,
                Map.of("fId", priceHistoryId)
        );
    }
}
