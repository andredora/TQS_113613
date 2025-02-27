package main.java.tqs;

import java.util.Optional;

public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products";
    private final ISimpleHttpClient httpClient;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(Integer id) {
        String url = API_PRODUCTS + "/" + id;
        String jsonResponse = httpClient.doHttpGet(url);
        
        return Product.fromJson(jsonResponse);
    }
}
