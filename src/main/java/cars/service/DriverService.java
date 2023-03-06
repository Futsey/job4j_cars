package cars.service;

import cars.model.Car;
import cars.model.Driver;
import cars.store.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverRepository store;

    public boolean add(Driver driver) {
        return store.add(driver);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }

    public boolean update(Driver driver) {
        return store.update(driver);
    }

    public List<Driver> findAll() {
        return store.findAll();
    }

    public Optional<Driver> findById(int id) {
        return store.findById(id);
    }
}
