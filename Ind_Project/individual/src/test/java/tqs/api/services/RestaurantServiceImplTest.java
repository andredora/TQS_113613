package tqs.api.services;

import tqs.api.models.Restaurant;
import tqs.api.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        restaurantService = new RestaurantServiceImpl(restaurantRepository);
    }

    @Test
    void testGetRestaurants() {
        List<Restaurant> mockRestaurants = Arrays.asList(
                new Restaurant(1L, "Cantina de Santiago", "Aveiro", "08:00-20:00", LocalDate.now()),
                new Restaurant(2L, "Grelhados", "Aveiro", "09:00-21:00", LocalDate.now()));

        when(restaurantRepository.findAll()).thenReturn(mockRestaurants);

        List<Restaurant> result = restaurantService.getRestaurants();
        assertEquals(2, result.size());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void testGetRestaurantById_Found() {
        Restaurant mockRestaurant = new Restaurant(1L, "Cantina de Santiago", "Aveiro", "08:00-20:00", LocalDate.now());
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(mockRestaurant));

        Restaurant result = restaurantService.getRestaurantById(1L);
        assertEquals("Cantina de Santiago", result.getName());
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRestaurantById_NotFound() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.getRestaurantById(99L);
        });

        assertEquals("Restaurante não encontrado", exception.getMessage());
    }

    @Test
    void testGetRestaurantByName_Found() {
        Restaurant mockRestaurant = new Restaurant(1L, "Cantina de Santiago", "Aveiro", "08:00-20:00", LocalDate.now());
        when(restaurantRepository.findByName("Cantina de Santiago")).thenReturn(Optional.of(mockRestaurant));

        Restaurant result = restaurantService.getRestaurantByName("Cantina de Santiago");
        assertEquals("Aveiro", result.getLocation());
        verify(restaurantRepository, times(1)).findByName("Cantina de Santiago");
    }

    @Test
    void testGetRestaurantByName_NotFound() {
        when(restaurantRepository.findByName("Desconhecido")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.getRestaurantByName("Desconhecido");
        });

        assertEquals("Restaurante não encontrado com o nome: Desconhecido", exception.getMessage());
    }

    @Test
    void testAddRestaurant() {
        Restaurant newRestaurant = new Restaurant(0L, "RU3", "Local 3", "10:00-22:00", LocalDate.now());
        when(restaurantRepository.save(newRestaurant)).thenReturn(newRestaurant);

        Restaurant result = restaurantService.addRestaurant(newRestaurant);
        assertEquals("RU3", result.getName());
        verify(restaurantRepository, times(1)).save(newRestaurant);
    }
}
