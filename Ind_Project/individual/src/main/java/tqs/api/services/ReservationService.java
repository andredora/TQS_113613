package tqs.api.services;

import tqs.api.models.Reservation;
import java.util.List;

public interface ReservationService {
    List<Reservation> getReservations();

    Reservation getReservation(String userToken);

    Reservation addReservation(Reservation reservation);

    void cancelReservation(String userToken);

    void cancelReservationByMealId(Long mealId);
}
