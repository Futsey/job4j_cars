package cars.service.car;

import cars.model.Car;
import cars.store.car.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository store;

    public boolean add(Car car) {
        return store.add(car);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public boolean update(Car car) {
        return store.update(car);
    }

    public Set<Car> findAll() {
        return new HashSet<>(store.findAll());
    }

    public Optional<Car> findById(int id) {
        return store.findById(id);
    }
}
