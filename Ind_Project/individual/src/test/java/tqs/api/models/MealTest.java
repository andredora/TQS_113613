package tqs.api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class MealTest {

    @Test
    void testConstructor() {
        Restaurant restaurant = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        Meal meal = new Meal(1, restaurant, "Frango frito", "Servido com batata e arroz", 6, LocalDateTime.now(),
                "Cantina de Santiago");

        assertEquals(1, meal.getId());
        assertEquals("Frango frito", meal.getName());
        assertEquals("Servido com batata e arroz", meal.getDescription());
        assertEquals(6, meal.getPrice());
        assertNotNull(meal.getCreatedAt());
        assertEquals("Cantina de Santiago", meal.getRestaurantName());
    }

    @Test
    void testSettersAndGetters() {
        Restaurant restaurant = new Restaurant(1, "Grelhados", "Cacia", "10:00 - 23:00", LocalDate.now());
        Meal meal = new Meal();
        meal.setId(2);
        meal.setRestaurant(restaurant);
        meal.setName("Salmão");
        meal.setDescription("Servido com Salada");
        meal.setPrice(3);
        meal.setCreatedAt(LocalDateTime.now());
        meal.setRestaurantName("Grelhados");

        assertEquals(2, meal.getId());
        assertEquals("Salmão", meal.getName());
        assertEquals("Servido com Salada", meal.getDescription());
        assertEquals(3, meal.getPrice());
        assertNotNull(meal.getCreatedAt());
        assertEquals("Grelhados", meal.getRestaurantName());
    }

    @Test
    void testToString() {
        Restaurant restaurant = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        Meal meal = new Meal(1, restaurant, "Bacalhau", "Servido com batata cozida", 4.4, LocalDateTime.now(),
                "Cantina de Santiago");

        String expected = "Meal{id=1, restaurant=" + restaurant
                + ", name='Bacalhau', description='Servido com batata cozida', price=4.4, createdAt="
                + meal.getCreatedAt() + ", restaurantName='Cantina de Santiago'}";
        assertEquals(expected, meal.toString());
    }

    @Test
    void testEquals() {
        Restaurant restaurant1 = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        Restaurant restaurant2 = new Restaurant(1, "Cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());

        Meal meal1 = new Meal(1, restaurant1, "Frango frito", "Servido com arroz", 9.99, LocalDateTime.now(),
                "Cantina de Santiago");
        Meal meal2 = new Meal(1, restaurant2, "Frango frito", "Servido com arroz", 9.99, LocalDateTime.now(),
                "Cantina de Santiago");

        assertTrue(meal1.equals(meal2));
        assertTrue(meal1.equals(meal1));

        assertFalse(meal1.equals(null));

        meal2.setId(2);
        assertFalse(meal1.equals(meal2));
    }

}
