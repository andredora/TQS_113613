package tqs.api.repositories;

import java.util.Optional;
import tqs.api.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);

    Optional<Restaurant> findById(Long id);

}
