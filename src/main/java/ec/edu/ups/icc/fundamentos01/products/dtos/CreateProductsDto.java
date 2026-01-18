package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProductsDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    public Double price;

    @NotNull(message = "El stock es obligatorio")
    @DecimalMin(value = "0", message = "El stock no puede ser negativo")
    public Integer stock;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500)
    public String description;

    @NotNull(message = "El ID del dueño es obligatorio")
    public Long userId;

    // @NotNull(message = "El ID de la categoría es obligatorio")
    // public Long categoryId;

    @NotNull(message = "Debe especificar al menos una categoría")    
    @Size(min = 1, message = "Debe haber al menos una categoría asociada al producto")
    public Set<Long> categoryIds; //ingresemos un listado de ID de categorias a las que pertenece el producto [4,2]

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public Set<Long> getCategoriesId() {
        return categoryIds;
    }
    
    public void setCategoryId(Set<Long> categoryId) {
        this.categoryIds = categoryId;
    }



}