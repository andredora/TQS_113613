package tqs.api.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import tqs.api.repositories.WeatherRepository;
import tqs.api.models.Weather;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    // Injetando a URL da API
    @Value("${weather.api.url}")
    private String apiUrl;

    private final Map<String, String> cache = new HashMap<>();
    private int totalRequests = 0;
    private int cacheHits = 0;
    private int cacheMisses = 0;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String checkWeather() {
        totalRequests++;
        String key = "weather";

        if (cache.containsKey(key)) {
            cacheHits++;
            return cache.get(key);
        }

        cacheMisses++;

        // Usando a URL da API
        String url = apiUrl; // Aqui você está usando a URL diretamente, com a chave da API já configurada

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            cache.put(key, responseBody);
            return responseBody;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro ao buscar dados do clima";
        }
    }

    public Map<String, Integer> getCacheStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalRequests", totalRequests);
        stats.put("cacheHits", cacheHits);
        stats.put("cacheMisses", cacheMisses);
        return stats;
    }
}
