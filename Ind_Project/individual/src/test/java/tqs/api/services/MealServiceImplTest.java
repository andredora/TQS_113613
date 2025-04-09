package tqs.api.services;

import tqs.api.models.Meal;
import tqs.api.repositories.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealServiceImpl mealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMeals() {
        Meal meal1 = new Meal(1L, null, "Meal 1", "Description 1", 10.0, null, "Restaurante 1");
        Meal meal2 = new Meal(2L, null, "Meal 2", "Description 2", 15.0, null, "Restaurante 2");
        when(mealRepository.findAll()).thenReturn(Arrays.asList(meal1, meal2));

        var meals = mealService.getMeals();

        assertEquals(2, meals.size());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void testGetMealById_Found() {
        Meal meal = new Meal(1L, null, "Meal 1", "Description", 10.0, null, "Restaurante");
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        Meal result = mealService.getMealById(1L);

        assertEquals(meal, result);
        verify(mealRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMealById_NotFound() {
        when(mealRepository.findById(999L)).thenReturn(Optional.empty());

        Meal result = mealService.getMealById(999L);

        assertNull(result);
        verify(mealRepository, times(1)).findById(999L);
    }

    @Test
    void testAddMeal() {
        Meal meal = new Meal(1L, null, "Meal 1", "Description", 10.0, null, "Restaurante");
        when(mealRepository.save(meal)).thenReturn(meal);

        Meal result = mealService.addMeal(meal);

        assertEquals(meal, result);
        verify(mealRepository, times(1)).save(meal);
    }
}
