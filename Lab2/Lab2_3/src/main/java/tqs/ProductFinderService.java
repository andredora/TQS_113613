package tqs;

import java.io.IOException;
import java.util.Optional;

public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products";
    private final ISimpleHttpClient httpClient;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(Integer id) {
        String url = API_PRODUCTS + "/" + id;
        try {
            String jsonResponse = httpClient.doHttpGet(url); // Pode lançar IOException
            return Product.fromJson(jsonResponse);
        } catch (IOException e) {
            // Trate a exceção (por exemplo, log ou retorne Optional.empty())
            System.err.println("Erro ao buscar produto: " + e.getMessage());
            return Optional.empty(); // Retorna um Optional vazio em caso de erro
        }
    }
}