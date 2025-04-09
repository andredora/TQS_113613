package tqs.api.controllers;

import tqs.api.models.Meal;
import tqs.api.models.Reservation;
import tqs.api.models.Restaurant;
import tqs.api.services.MealService;
import tqs.api.services.ReservationService;
import tqs.api.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MealService mealService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    void testCreateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1, "cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        when(restaurantService.addRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        mockMvc.perform(post("/api/add_restaurant")
                .contentType("application/json")
                .content(
                        "{\"name\": \"cantina de Santiago\", \"location\": \"Aveiro\", \"openingHours\": \"9:00 - 22:00\", \"createdAt\": \"2025-04-07\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("cantina de Santiago"))
                .andExpect(jsonPath("$.location").value("Aveiro"));
    }

    @Test
    void testCreateMeal() throws Exception {
        Restaurant restaurant = new Restaurant(1, "cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        Meal meal = new Meal(1, restaurant, "Frango frito", "Servido com arroz", 9.99, LocalDateTime.now(),
                "cantina de Santiago");

        when(restaurantService.getRestaurantByName("cantina de Santiago")).thenReturn(restaurant);
        when(mealService.addMeal(any(Meal.class))).thenReturn(meal);

        mockMvc.perform(post("/api/add_meal")
                .contentType("application/json")
                .content(
                        "{\"name\": \"Frango frito\", \"description\": \"Servido com arroz\", \"price\": 9.99, \"restaurantName\": \"cantina de Santiago\", \"createdAt\": \"2025-04-07T15:30:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Frango frito"))
                .andExpect(jsonPath("$.price").value(9.99));
    }

    @Test
    void testCreateReservation() throws Exception {
        Restaurant restaurant = new Restaurant(1, "cantina de Santiago", "Aveiro", "9:00 - 22:00", LocalDate.now());
        Meal meal = new Meal(1, restaurant, "Frango frito", "Servido com arroz", 9.99, LocalDateTime.now(),
                "cantina de Santiago");
        Reservation reservation = new Reservation(1, "token123", meal, LocalDateTime.now(), LocalDateTime.now());

        when(mealService.getMealById(1L)).thenReturn(meal);
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/api/reserve")
                .contentType("application/json")
                .content(
                        "{\"userToken\": \"token123\", \"meal\": {\"id\": 1}, \"reservationDate\": \"2025-04-07T15:30:00\", \"createdAt\": \"2025-04-07T15:30:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userToken").value("token123"))
                .andExpect(jsonPath("$.meal.name").value("Frango frito"));
    }

    @Test
    void testGetReservations() throws Exception {
        Reservation reservation1 = new Reservation(1, "token123", new Meal(), LocalDateTime.now(), LocalDateTime.now());
        Reservation reservation2 = new Reservation(2, "token456", new Meal(), LocalDateTime.now(), LocalDateTime.now());

        when(reservationService.getReservations()).thenReturn(Arrays.asList(reservation1, reservation2));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userToken").value("token123"))
                .andExpect(jsonPath("$[1].userToken").value("token456"));
    }

    @Test
    void testGetReservation() throws Exception {
        Reservation reservation = new Reservation(1, "token123", new Meal(), LocalDateTime.now(), LocalDateTime.now());

        when(reservationService.getReservation("token123")).thenReturn(reservation);

        mockMvc.perform(get("/api/reservation/token123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userToken").value("token123"));
    }

    @Test
    void testCancelReservation() throws Exception {
        doNothing().when(reservationService).cancelReservation("token123");

        mockMvc.perform(delete("/api/reservation/token123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva cancelada com sucesso!"));
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "Restaurante A", "Aveiro", "9:00 - 22:00", LocalDate.now()));
        restaurantList.add(new Restaurant(2, "Restaurante B", "Aveiro", "9:00 - 22:00", LocalDate.now()));

        when(restaurantService.getRestaurants()).thenReturn(restaurantList);

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Restaurante A"))
                .andExpect(jsonPath("$[1].name").value("Restaurante B"));
    }

    @Test
    void testCancelReservationByMealId() throws Exception {
        doNothing().when(reservationService).cancelReservationByMealId(1L);

        mockMvc.perform(delete("/api/reservation/meal/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva cancelada com sucesso!"));
    }

    @Test
    void testGetMealsByRestaurant() throws Exception {
        Long restaurantId = 1L;
        Meal meal1 = new Meal();
        meal1.setRestaurant(new Restaurant(restaurantId, "Restaurante A", "Aveiro", "9:00 - 22:00", LocalDate.now()));
        Meal meal2 = new Meal();
        meal2.setRestaurant(new Restaurant(2L, "Restaurante B", "Aveiro", "9:00 - 22:00", LocalDate.now()));

        List<Meal> meals = Arrays.asList(meal1, meal2);

        when(mealService.getMeals()).thenReturn(meals);

        mockMvc.perform(get("/api/meals/restaurant/" + restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurant.id").value(restaurantId))
                .andExpect(jsonPath("$[0].restaurant.name").value("Restaurante A"));
    }

    @Test
    void testGetAllMeals() throws Exception {
        // Criação de duas refeições mockadas
        Meal meal1 = new Meal(1L, new Restaurant(1, "Cantina", "Aveiro", "9:00 - 22:00", LocalDate.now()),
                "Frango Frito", "Servido com arroz", 9.99, LocalDateTime.now(), "Cantina");
        Meal meal2 = new Meal(2L, new Restaurant(1, "Cantina", "Aveiro", "9:00 - 22:00", LocalDate.now()), "Bacalhau",
                "Servido com batatas", 12.99, LocalDateTime.now(), "Cantina");

        // Mock do serviço que retorna a lista de refeições
        when(mealService.getMeals()).thenReturn(Arrays.asList(meal1, meal2));

        // Realiza a requisição GET para o endpoint "/api/meals"
        mockMvc.perform(get("/api/meals"))
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$[0].name").value("Frango Frito")) // Verifica o nome da primeira refeição
                .andExpect(jsonPath("$[1].name").value("Bacalhau")) // Verifica o nome da segunda refeição
                .andExpect(jsonPath("$[0].price").value(9.99)) // Verifica o preço da primeira refeição
                .andExpect(jsonPath("$[1].price").value(12.99)); // Verifica o preço da segunda refeição
    }

    @Test
    void testCreateMealRestaurantNotFound() throws Exception {
        // Restaurante não encontrado, será simulado o erro
        when(restaurantService.getRestaurantByName("Cantina de Santiago")).thenReturn(null);

        // Execução da requisição POST e verificação da resposta de erro
        mockMvc.perform(post("/api/add_meal")
                .contentType("application/json")
                .content(
                        "{\"name\": \"Frango frito\", \"description\": \"Servido com arroz\", \"price\": 9.99, \"restaurantName\": \"Cantina de Santiago\", \"createdAt\": \"2025-04-07T15:30:00\"}"))
                .andExpect(status().isNotFound()) // Espera que a resposta seja 404 NOT FOUND
                .andExpect(content().string("Restaurante com o nome Cantina de Santiago não encontrado."));
    }

    @Test
    void testCreateMealBadRequest() throws Exception {
        // Enviando uma refeição sem dados obrigatórios
        mockMvc.perform(post("/api/add_meal")
                .contentType("application/json")
                .content("{\"name\": \"Frango frito\", \"description\": \"Servido com arroz\", \"price\": 9.99}")) // Falta
                                                                                                                   // o
                                                                                                                   // restaurantName
                .andExpect(status().isBadRequest()) // Espera que a resposta seja 400 BAD REQUEST
                .andExpect(content().string("Nome do restaurante e data da refeição são obrigatórios."));
    }

    @Test
    void testCreateMealInternalServerError() throws Exception {
        // Simulando uma exceção no serviço de refeição
        when(restaurantService.getRestaurantByName("Cantina de Santiago")).thenReturn(new Restaurant());
        when(mealService.addMeal(any(Meal.class))).thenThrow(new RuntimeException("Erro inesperado"));

        // Execução da requisição POST e verificação da resposta de erro
        mockMvc.perform(post("/api/add_meal")
                .contentType("application/json")
                .content(
                        "{\"name\": \"Frango frito\", \"description\": \"Servido com arroz\", \"price\": 9.99, \"restaurantName\": \"Cantina de Santiago\", \"createdAt\": \"2025-04-07T15:30:00\"}"))
                .andExpect(status().isInternalServerError()) // Espera que a resposta seja 500 INTERNAL SERVER ERROR
                .andExpect(content().string("Erro ao criar refeição: Erro inesperado"));
    }

}
