package tqs.api.services;

import tqs.api.models.Restaurant;
import java.util.List;

public interface RestaurantService {
    List<Restaurant> getRestaurants();

    Restaurant getRestaurantById(Long id);

    Restaurant getRestaurantByName(String name);

    Restaurant addRestaurant(Restaurant restaurant);
}
