package cars;

import cars.model.User;
import cars.store.UserRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class UserUsage {

    private static final String SEPARATOR = "____________________".concat(System.lineSeparator());

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            var userRepository = new UserRepository(sf);
            var user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            userRepository.create(user);
            System.out.println(SEPARATOR);
            System.out.println(user + " created!");
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeLogin("e")
                    .forEach(System.out::println);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.findByLogin("admin")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            user.setLogin("Vasya");
            user.setPassword("qwe");
            userRepository.update(user);
            System.out.println(SEPARATOR);
            System.out.println(user + " updated!");
            System.out.println(SEPARATOR);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.delete(user.getId());
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
