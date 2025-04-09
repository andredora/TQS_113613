package tqs.api.services;

import tqs.api.repositories.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(weatherService, "apiUrl", "http://mock.url");
    }

    @Test
    void testCheckWeather_cacheHit() {
        ReflectionTestUtils.setField(weatherService, "apiUrl", "http://dummy.url");

        Map<String, String> cache = (Map<String, String>) ReflectionTestUtils.getField(weatherService, "cache");
        cache.put("weather", "mocked weather");

        String result = weatherService.checkWeather();

        assertEquals("mocked weather", result);
    }

    @Test
    void testGetCacheStats() {
        ReflectionTestUtils.setField(weatherService, "apiUrl", "http://dummy.url");

        weatherService.checkWeather();

        ReflectionTestUtils.setField(weatherService, "cache", Map.of("weather", "fake data"));

        weatherService.checkWeather();

        Map<String, Integer> stats = weatherService.getCacheStats();

        assertEquals(2, stats.get("totalRequests"));
        assertEquals(1, stats.get("cacheHits"));
        assertEquals(1, stats.get("cacheMisses"));
    }

}