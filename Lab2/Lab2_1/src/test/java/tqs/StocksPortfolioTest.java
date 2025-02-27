package test.java.tqs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import main.java.tqs.*;

import java.util.List;

public class StocksPortfolioTest {

    @Mock
    private IStockmarketService stockmarketServiceMock;

    private StocksPortfolio portfolio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        portfolio = new StocksPortfolio(stockmarketServiceMock);
    }

    @Test
    public void testTotalValue() {
        when(stockmarketServiceMock.lookUpPrice("AAPL")).thenReturn(150.0);
        when(stockmarketServiceMock.lookUpPrice("GOOG")).thenReturn(2800.0);
        when(stockmarketServiceMock.lookUpPrice("TSLA")).thenReturn(700.0);  
        when(stockmarketServiceMock.lookUpPrice("AMZN")).thenReturn(3300.0); 

        portfolio.addStock(new Stock("AAPL", 2));
        portfolio.addStock(new Stock("GOOG", 1));
        portfolio.addStock(new Stock("TSLA", 2));

        double totalValue = portfolio.totalValue();

        assertEquals(4500.0, totalValue, 0.01);
        assertThat(totalValue, is(closeTo(4500.0, 0.01)));

        verify(stockmarketServiceMock).lookUpPrice("AAPL");
        verify(stockmarketServiceMock).lookUpPrice("GOOG");
        verify(stockmarketServiceMock, times(3)).lookUpPrice(anyString());
    }

    @Test
    public void testMostValuableStocks() {
        when(stockmarketServiceMock.lookUpPrice("AAPL")).thenReturn(150.0);
        when(stockmarketServiceMock.lookUpPrice("GOOG")).thenReturn(2800.0);
        when(stockmarketServiceMock.lookUpPrice("TSLA")).thenReturn(700.0);
        when(stockmarketServiceMock.lookUpPrice("AMZN")).thenReturn(3300.0);

        portfolio.addStock(new Stock("AAPL", 2)); 
        portfolio.addStock(new Stock("GOOG", 1)); 
        portfolio.addStock(new Stock("TSLA", 2));
        portfolio.addStock(new Stock("AMZN", 1));  

        List<Stock> topStocks = portfolio.mostValuableStocks(3);

        assertEquals(3, topStocks.size());

        assertThat(topStocks.get(0).getLabel(), is("AMZN"));
        assertThat(topStocks.get(1).getLabel(), is("GOOG"));
        assertThat(topStocks.get(2).getLabel(), is("TSLA"));


    }
}
