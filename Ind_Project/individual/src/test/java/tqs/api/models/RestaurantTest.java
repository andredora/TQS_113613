package tqs.api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

class RestaurantTest {

    @Test
    void testConstructor() {
        Restaurant restaurant = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        assertEquals(1, restaurant.getId());
        assertEquals("Cantina de Santiago", restaurant.getName());
        assertEquals("Aveiro", restaurant.getLocation());
        assertEquals("9:00 - 22:00", restaurant.getOpeningHours());
        assertNotNull(restaurant.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(2);
        restaurant.setName("Grelhados");
        restaurant.setLocation("Cacia");
        restaurant.setOpeningHours("10:00 - 23:00");
        restaurant.setCreatedAt(LocalDate.now());

        assertEquals(2, restaurant.getId());
        assertEquals("Grelhados", restaurant.getName());
        assertEquals("Cacia", restaurant.getLocation());
        assertEquals("10:00 - 23:00", restaurant.getOpeningHours());
        assertNotNull(restaurant.getCreatedAt());
    }

    @Test
    void testEquals() {
        Restaurant restaurant1 = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        Restaurant restaurant2 = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());

        assertTrue(restaurant1.equals(restaurant2));
        assertTrue(restaurant1.equals(restaurant1));

        assertFalse(restaurant1.equals(null));

        restaurant2.setId(2);
        assertFalse(restaurant1.equals(restaurant2));
    }

    @Test
    void testToString() {
        Restaurant restaurant = new Restaurant(1, "Cantina 3d", "Aveiro", "8:00 - 20:00", LocalDate.now());
        String expected = "Restaurant{id=1, name='Cantina 3d', location='Aveiro', openingHours='8:00 - 20:00', createdAt="
                + restaurant.getCreatedAt() + "}";
        assertEquals(expected, restaurant.toString());
    }
}
