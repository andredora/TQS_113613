package tqs;

import org.json.JSONObject;
import java.util.Optional;

public class Product {
    private Integer id;
    private String image;
    private String description;
    private Double price;
    private String title;
    private String category;

    public Product() {}
    
    public Product(Integer id, String image, Double price, String title, String category, String description) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.title = title;
        this.category = category;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static Optional<Product> fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return Optional.empty();
        }
        
        JSONObject jsonObject = new JSONObject(json);
        Product product = new Product(
            jsonObject.getInt("id"),
            jsonObject.getString("image"),
            jsonObject.getDouble("price"),
            jsonObject.getString("title"),
            jsonObject.getString("category"),
            jsonObject.getString("description")
        );
        return Optional.of(product);
    }
}