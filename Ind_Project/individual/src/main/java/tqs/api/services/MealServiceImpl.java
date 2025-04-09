package tqs.api.services;

import tqs.api.models.Meal;
import tqs.api.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository mealRepository;

    @Override
    public List<Meal> getMeals() {
        return mealRepository.findAll();
    }

    @Override
    public Meal getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    @Override
    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }
}
