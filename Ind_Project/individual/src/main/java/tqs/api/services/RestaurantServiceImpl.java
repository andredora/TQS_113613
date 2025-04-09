package tqs.api.services;

import tqs.api.models.Restaurant;
import tqs.api.repositories.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    }

    @Override
    public Restaurant getRestaurantByName(String name) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByName(name);
        return restaurantOpt.orElseThrow(() -> new RuntimeException("Restaurante não encontrado com o nome: " + name));
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

}
