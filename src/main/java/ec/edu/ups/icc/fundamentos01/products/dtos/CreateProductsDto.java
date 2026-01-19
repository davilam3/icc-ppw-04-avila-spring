package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProductsDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    public double price;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    public String description;

    // ============== RELACIONES ==============

    @NotNull(message = "El ID del usuario es obligatorio")
    public Long userId;

    @NotNull(message = "El Id del usuario es obligatoria")
    public Long categoryId;

    // @NotNull(message = "El ID de la categoría es obligatorio")
    // public Long categoryId;
    @NotNull(message = "Los IDs de las categorías son obligatorios")
    @Size(min = 1, message = "Debe ingresar al menos un ID de categoría")
    public List<Long> categoryIds; // Ingresemos un listado de IDs de categorias [4-2]

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

}