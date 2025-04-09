package tqs.api.services;

import tqs.api.models.Meal;
import java.util.List;

public interface MealService {
    List<Meal> getMeals();

    Meal getMealById(Long id);

    Meal addMeal(Meal meal);
}
