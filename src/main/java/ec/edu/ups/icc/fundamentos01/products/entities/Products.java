package ec.edu.ups.icc.fundamentos01.products.entities;

public class Products {
    private int id;
    private String name;
    private String description;
    private double price;
    private String createdAt;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Products() {
    }

    public Products(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    public Products(int id, String name, String description, double price, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    // getters y setters

}
