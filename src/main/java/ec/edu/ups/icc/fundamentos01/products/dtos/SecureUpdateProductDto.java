package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SecureUpdateProductDto {

    public Double price;
    public String reason;

    @NotBlank
    @Size(min = 3, max = 150)
    public String name;

    @NotBlank
    @Size(min = 5, max = 500)
    public String description;

    // Relaciones

    @NotNull(message = "El ID del usuario es obligatorio")
    public Long userId;

    // @NotNull(message = "El ID de la categoría es obligatorio")
    // public Long categoryId;

    @NotNull(message = "Los IDs de las categorías son obligatorios")
    @Size(min = 1, message = "Debe ingresar al menos un ID de categoría")
    public List<Long> categoryIds;

}
