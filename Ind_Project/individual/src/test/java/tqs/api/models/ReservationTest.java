package tqs.api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class ReservationTest {

    @Test
    void testConstructor() {
        Meal meal = new Meal(1, new Restaurant(), "Frango frito", "Description", 10.0, LocalDateTime.now(),
                "Cantina de Santiago");
        Reservation reservation = new Reservation(1, "user-token", meal, LocalDateTime.now(), LocalDateTime.now());

        assertEquals(1, reservation.getId());
        assertEquals("user-token", reservation.getUserToken());
        assertEquals(meal, reservation.getMeal());
        assertNotNull(reservation.getReservationDate());
        assertNotNull(reservation.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        Meal meal = new Meal(2, new Restaurant(), "Batatas com arroz", "Description", 15.0, LocalDateTime.now(),
                "Cantina 3d");
        Reservation reservation = new Reservation();
        reservation.setId(2);
        reservation.setUserToken("user-token-2");
        reservation.setMeal(meal);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setCreatedAt(LocalDateTime.now());

        assertEquals(2, reservation.getId());
        assertEquals("user-token-2", reservation.getUserToken());
        assertEquals(meal, reservation.getMeal());
        assertNotNull(reservation.getReservationDate());
        assertNotNull(reservation.getCreatedAt());
    }

    @Test
    void testToString() {
        Meal meal = new Meal(1, new Restaurant(), "Omelete", "Description", 12.99, LocalDateTime.now(), "Grelhados");
        Reservation reservation = new Reservation(1, "user-token-3", meal, LocalDateTime.now(), LocalDateTime.now());

        String expected = "Reservation{id=1, userToken='user-token-3', meal=" + meal + ", reservationDate="
                + reservation.getReservationDate() + ", createdAt=" + reservation.getCreatedAt() + "}";
        assertEquals(expected, reservation.toString());
    }

    @Test
    void testEquals() {
        Meal meal = new Meal(1, new Restaurant(), "Frango frito", "Description", 10.0, LocalDateTime.now(),
                "Cantina de Santiago");

        Reservation reservation1 = new Reservation(1, "user-token", meal, LocalDateTime.now(), LocalDateTime.now());
        Reservation reservation2 = new Reservation(1, "user-token", meal, LocalDateTime.now(), LocalDateTime.now());

        assertTrue(reservation1.equals(reservation2));

        assertTrue(reservation1.equals(reservation1));

        assertFalse(reservation1.equals(null));

        reservation2.setId(2);
        assertFalse(reservation1.equals(reservation2));
    }

}
