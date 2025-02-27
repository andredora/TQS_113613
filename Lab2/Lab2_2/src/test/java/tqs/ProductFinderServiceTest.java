package test.java.tqs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import main.java.tqs.ProductFinderService;
import main.java.tqs.ISimpleHttpClient;
import main.java.tqs.Product;

class ProductFinderServiceTest {
    private ProductFinderService productFinderService;
    private ISimpleHttpClient httpClientMock;

    @BeforeEach
    void setUp() {
        httpClientMock = mock(ISimpleHttpClient.class);
        productFinderService = new ProductFinderService(httpClientMock);
    }

    @Test
    void testFindProductDetails_ValidJsonResponse_ShouldReturnProduct() {
        // Mocking API response
        String jsonResponse = "{"
                + "\"id\": 1,"
                + "\"title\": \"Test Product\","
                + "\"price\": 29.99,"
                + "\"description\": \"A sample product description\","
                + "\"category\": \"electronics\","
                + "\"image\": \"https://example.com/image.jpg\""
                + "}";

        when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/1")).thenReturn(jsonResponse);

        // Executing the method
        Optional<Product> productOptional = productFinderService.findProductDetails(1);

        // Assertions
        assertTrue(productOptional.isPresent());
        Product product = productOptional.get();
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getTitle());
        assertEquals(29.99, product.getPrice());
        assertEquals("A sample product description", product.getDescription());
        assertEquals("electronics", product.getCategory());
        assertEquals("https://example.com/image.jpg", product.getImage());
    }

    @Test
    void testFindProductDetails_ValidProduct() {
        String jsonResponse = "{"
        + "\"id\":3,"
        + "\"title\":\"Mens Cotton Jacket\","
        + "\"price\":55.99,"
        + "\"category\":\"men's clothing\","
        + "\"image\":\"some_url\","
        + "\"description\":\"A great jacket\""
        + "}";
       when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);

        Optional<Product> product = productFinderService.findProductDetails(3);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());
    }
    
    @Test
    void testFindProductDetails_ProductNotFound() {
        when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/300")).thenReturn("");

        Optional<Product> product = productFinderService.findProductDetails(300);

        assertFalse(product.isPresent());
    }
}