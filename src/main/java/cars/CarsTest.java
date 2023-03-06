package cars;

import cars.model.Car;
import cars.model.Driver;
import cars.model.Engine;
import cars.model.User;
import cars.service.CarService;
import cars.service.DriverService;
import cars.service.EngineService;
import cars.service.UserService;
import cars.store.*;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.Set;

public class CarsTest {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            @Cleanup SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            @Cleanup Session session = sf.openSession();
            CrudRepository crudRepository = new CrudRepository(sf);
            UserRepository userRepository = new UserRepository(crudRepository);
            UserService userService = new UserService(userRepository);
            DriverRepository driverRepository = new DriverRepository(crudRepository);
            DriverService driverService = new DriverService(driverRepository);
            EngineRepository engineRepository = new EngineRepository(crudRepository);
            EngineService engineService = new EngineService(engineRepository);
            CarRepository carRepository = new CarRepository(crudRepository);
            CarService carService = new CarService(carRepository);

            User user = User.builder()
                    .login("user")
                    .password("pass")
                    .build();
            User user1 = User.builder()
                    .login("user1")
                    .password("pass")
                    .build();
            userService.add(user);
            userService.add(user1);

            Driver driver1 = Driver.builder()
                    .name("driver1")
                    .user(userService.findById(4).orElse(null))
                    .build();
            Driver driver2 = Driver.builder()
                    .name("driver2")
                    .user(userService.findById(5).orElse(null))
                    .build();
            driverService.add(driver1);
            driverService.add(driver2);
            Set<Driver> owners = Set.of(driver1, driver2);

            Engine engine = Engine.builder()
                    .name("2AZ-FE")
                    .build();
            engineService.add(engine);

            Car car = Car.builder()
                    .name("Lada")
                    .engine(engineService.findById(1).get())
                    .owners(new HashSet<>(driverService.findAll()))
                    .build();
            carService.add(car);

            System.out.println("________________________________");
            System.out.println("User: ".concat(String.valueOf(user)));
            System.out.println("User1: ".concat(String.valueOf(user1)));
            System.out.println("UserInDB: ".concat(String.valueOf(userService.findById(4).orElse(null))));
            System.out.println("User1InDB: ".concat(String.valueOf(userService.findById(5).orElse(null))));
            System.out.println(System.lineSeparator());
            System.out.println("Driver1: ".concat(owners.toString()));
            System.out.println("Driver1InDB: ".concat(String.valueOf(driverService.findById(1).orElse(null))));
            System.out.println("Driver2InDB: ".concat(String.valueOf(driverService.findById(2).orElse(null))));
            System.out.println(System.lineSeparator());
            System.out.println("Engine: ".concat(String.valueOf(engine)));
            System.out.println("EngineInDB: ".concat(String.valueOf(engineService.findById(1))));
            System.out.println(System.lineSeparator());
            System.out.println("Car: ".concat(String.valueOf(car)));
            System.out.println("CarInDB: ".concat(String.valueOf(carService.findById(6))));
            System.out.println(System.lineSeparator());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
