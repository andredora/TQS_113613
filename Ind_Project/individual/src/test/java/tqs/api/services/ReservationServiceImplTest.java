import tqs.api.models.Reservation;
import tqs.api.repositories.ReservationRepository;
import tqs.api.services.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    public void setup() {
        reservation = new Reservation();
        reservation.setUserToken("token123");
        reservation.setMeal(null);
    }

    @Test
    void testGetReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));
        List<Reservation> reservations = reservationService.getReservations();
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals("token123", reservations.get(0).getUserToken());
    }

    @Test
    void testGetReservation() {
        when(reservationRepository.findByUserToken("token123")).thenReturn(reservation);
        Reservation result = reservationService.getReservation("token123");
        assertNotNull(result);
        assertEquals("token123", result.getUserToken());
    }

    @Test
    void testAddReservation() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation result = reservationService.addReservation(reservation);
        assertNotNull(result);
        assertEquals("token123", result.getUserToken());
    }

    @Test
    void testCancelReservation() {
        when(reservationRepository.findByUserToken("token123")).thenReturn(reservation);
        doNothing().when(reservationRepository).delete(reservation);
        reservationService.cancelReservation("token123");
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void testCancelReservationNotFound() {
        when(reservationRepository.findByUserToken("token123")).thenReturn(null);
        reservationService.cancelReservation("token123");
        verify(reservationRepository, times(0)).delete(any(Reservation.class));
    }

    @Test
    void testCancelReservationByMealId() {
        when(reservationRepository.findByMealId(1L)).thenReturn(reservation);
        doNothing().when(reservationRepository).delete(reservation);
        reservationService.cancelReservationByMealId(1L);
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void testCancelReservationByMealIdNotFound() {
        when(reservationRepository.findByMealId(1L)).thenReturn(null);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            reservationService.cancelReservationByMealId(1L);
        });
        assertEquals("Reserva não encontrada para a refeição com ID: 1", exception.getMessage());
    }
}
