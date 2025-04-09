package tqs.api.repositories;

import tqs.api.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByUserToken(String userToken);

    Reservation findByMealId(Long mealId);

}
