
package tqs;

import main.java.tqs.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;


public class StockTest {

    Stock stock;

    @BeforeEach
    public void setup() {
        stock = new Stock("AAPL", 100);    
    }


    @Test
    public void testGetLabel() {
        assertEquals("AAPL", stock.getLabel());
    }

    @Test
    public void testSetLabel() {
        stock.setLabel("GOOGL");
        assertEquals("GOOGL", stock.getLabel());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(100, stock.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        stock.setQuantity(200);
        assertEquals(200, stock.getQuantity());
    }
}
