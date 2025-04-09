package tqs.api.controllers;

import tqs.api.services.WeatherService;
import tqs.api.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public String getWeather() {
        return weatherService.checkWeather();
    }

    @GetMapping("/stats")
    public Map<String, Integer> getCacheStats() {
        return weatherService.getCacheStats();
    }
}
