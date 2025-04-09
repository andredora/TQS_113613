package tqs.api.controllers;

import org.springframework.web.server.ResponseStatusException;
import tqs.api.models.Meal;
import tqs.api.models.Reservation;
import tqs.api.models.Restaurant;
import tqs.api.services.MealService;
import tqs.api.services.ReservationService;
import tqs.api.services.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000/")
@Tag(name = "Reservas e Restaurantes", description = "Endpoints para manipular restaurantes, refeições e reservas")
public class ReservationController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MealService mealService;

    @Autowired
    private ReservationService reservationService;

    @Operation(summary = "Lista todos os restaurantes", description = "Este endpoint retorna todos os restaurantes cadastrados.")
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getRestaurants();
    }

    @Operation(summary = "Adiciona um novo restaurante", description = "Este endpoint cria um novo restaurante a partir dos dados fornecidos.")
    @PostMapping("/add_restaurant")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @Operation(summary = "Lista todas as refeições", description = "Este endpoint retorna todas as refeições disponíveis.")
    @GetMapping("/meals")
    public List<Meal> getAllMeals() {
        return mealService.getMeals();
    }

    @Operation(summary = "Adiciona uma nova refeição", description = "Este endpoint cria uma nova refeição associada a um restaurante.")
    @PostMapping("/add_meal")
    public ResponseEntity<?> createMeal(@RequestBody Meal meal) {
        try {
            if (meal.getRestaurantName() == null || meal.getCreatedAt() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Nome do restaurante e data da refeição são obrigatórios.");
            }

            Restaurant restaurant = restaurantService.getRestaurantByName(meal.getRestaurantName());

            if (restaurant == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Restaurante com o nome " + meal.getRestaurantName() + " não encontrado.");
            }

            meal.setRestaurant(restaurant);
            Meal createdMeal = mealService.addMeal(meal);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar refeição: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista as refeições de um restaurante", description = "Este endpoint retorna as refeições de um restaurante específico com base no ID do restaurante.")
    @GetMapping("/meals/restaurant/{restaurantId}")
    public List<Meal> getMealsByRestaurant(@PathVariable Long restaurantId) {
        return mealService.getMeals().stream()
                .filter(meal -> meal.getRestaurant().getId() == restaurantId)
                .toList();
    }

    @Operation(summary = "Lista todas as reservas", description = "Este endpoint retorna todas as reservas feitas até o momento.")
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @Operation(summary = "Faz uma reserva", description = "Este endpoint permite criar uma nova reserva associada a uma refeição.")
    @PostMapping("/reserve")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            Meal meal = mealService.getMealById(reservation.getMeal().getId());

            reservation.setReservationDate(LocalDateTime.now());
            reservation.setCreatedAt(LocalDateTime.now());
            reservation.setMeal(meal);

            Reservation createdReservation = reservationService.addReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar reserva: " + e.getMessage());
        }
    }

    @Operation(summary = "Consulta uma reserva pelo token do usuário", description = "Este endpoint retorna a reserva associada a um determinado token de usuário.")
    @GetMapping("/reservation/{userToken}")
    public Reservation getReservation(@PathVariable String userToken) {
        return reservationService.getReservation(userToken);
    }

    @Operation(summary = "Cancela uma reserva", description = "Este endpoint cancela a reserva associada ao token do usuário fornecido.")
    @DeleteMapping("/reservation/{userToken}")
    public String cancelReservation(@PathVariable String userToken) {
        reservationService.cancelReservation(userToken);
        return "Reserva cancelada com sucesso!";
    }

    @Operation(summary = "Cancela uma reserva pela refeição", description = "Este endpoint cancela a reserva associada ao ID de uma refeição.")
    @DeleteMapping("/reservation/meal/{mealId}")
    public ResponseEntity<String> cancelReservationByMealId(@PathVariable Long mealId) {
        try {
            reservationService.cancelReservationByMealId(mealId);
            return ResponseEntity.ok("Reserva cancelada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar reserva: " + e.getMessage());
        }
    }
}
