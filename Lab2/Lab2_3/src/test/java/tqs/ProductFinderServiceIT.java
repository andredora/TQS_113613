package tqs;

import tqs.TqsBasicHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductFinderServiceIT {
    private ProductFinderService productFinderService;

    @BeforeEach
    public void setUp() {
        // Usa a implementação real do HttpClient
        productFinderService = new ProductFinderService(new TqsBasicHttpClient());
    }

    @Test
    public void testFindProductDetails() {
        // Testa a busca de um produto com ID 1
        Optional<Product> product = productFinderService.findProductDetails(1);
        
        // Verifica se o produto foi encontrado
        assertThat(product.isPresent(), is(true));
        
        // Verifica os detalhes do produto
        assertThat(product.get().getId(), equalTo(1));
        assertThat(product.get().getTitle(), notNullValue());
        assertThat(product.get().getPrice(), greaterThan(0.0));
    }
}