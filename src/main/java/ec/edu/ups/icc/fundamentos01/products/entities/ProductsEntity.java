package ec.edu.ups.icc.fundamentos01.products.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.core.entities.BaseModel;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductsEntity extends BaseModel {

    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(length = 500)
    private String description;

    // ================== RELACIONES 1:N ==================

    /**
     * Relación Many-to-One con User
     * Muchos productos pertenecen a un usuario (owner/creator)
     */

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    /**
     * Relación Many-to-One con Category
     * Muchos productos pertenecen a una categoría
     */
    // @ManyToOne(optional = false, fetch = FetchType.LAZY)
    // @JoinColumn(name = "category_id", nullable = false)
    // private CategoryEntity category;

    /**
     * Relación Many-to-Many con Category
     * Un producto puede tener múltiples categorías
     * Una categoría puede estar en múltiples productos
     */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_categories", // Tabla intermedia
            joinColumns = @JoinColumn(name = "product_id"), // FK hacia products
            inverseJoinColumns = @JoinColumn(name = "category_id") // FK hacia categories
    )

    private Set<CategoryEntity> categories = new HashSet<>();

    public ProductsEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public void addCategory(CategoryEntity category) {
        this.categories.add(category);
    }

    /**
     * Remueve una categoría del producto y sincroniza la relación bidireccional
     */

    public void removeCategory(CategoryEntity category) {
        this.categories.remove(category);
    }

    public void clearCategories() {
        for (CategoryEntity category : new HashSet<>(this.categories)) {
            category.getProducts().remove(this);
        }
        this.categories.clear();
    }

}
