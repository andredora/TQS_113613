package tqs.api.services;

import tqs.api.models.Reservation;
import tqs.api.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservation(String userToken) {
        return reservationRepository.findByUserToken(userToken);
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void cancelReservation(String userToken) {
        Reservation reservation = reservationRepository.findByUserToken(userToken);
        if (reservation != null) {
            reservationRepository.delete(reservation);
        }

    }

    @Override
    public void cancelReservationByMealId(Long mealId) {
        Reservation reservation = reservationRepository.findByMealId(mealId);

        if (reservation != null) {
            reservationRepository.delete(reservation);
        } else {
            throw new RuntimeException("Reserva não encontrada para a refeição com ID: " + mealId);
        }
    }
}
