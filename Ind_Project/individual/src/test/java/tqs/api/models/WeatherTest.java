package tqs.api.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class WeatherTest {

    private Weather weather;

    @BeforeEach
    void setUp() {
        weather = new Weather(LocalDate.now(), 25.0, 60.0);
    }

    @Test
    void testWeatherConstructor() {
        assertNotNull(weather);
        assertEquals(25.0, weather.getTemperature());
        assertEquals(60.0, weather.getHumidity());
        assertNotNull(weather.getDate());
    }

    @Test
    void testGetAndSetId() {
        weather.setId(1L);
        assertEquals(1L, weather.getId());
    }

    @Test
    void testGetAndSetDate() {
        LocalDate newDate = LocalDate.of(2025, 12, 25);
        weather.setDate(newDate);
        assertEquals(newDate, weather.getDate());
    }

    @Test
    void testGetAndSetTemperature() {
        weather.setTemperature(30.0);
        assertEquals(30.0, weather.getTemperature());
    }

    @Test
    void testGetAndSetHumidity() {
        weather.setHumidity(75.0);
        assertEquals(75.0, weather.getHumidity());
    }

    @Test
    void testToString() {
        String expectedString = "Weather{id=null, date=" + weather.getDate() + ", temperature=25.0, humidity=60.0}";
        assertEquals(expectedString, weather.toString());
    }
}
