package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SecureUpdateProductDto {
    public String name;
    public Double price;
    public String description;
    public String reason;
    public Long categoryId;

    // ============== RELACIONES ==============

    @NotNull(message = "El ID del usuario es obligatorio")
    public Long userId;

    // ============== n:m ==============

    // @NotNull(message = "El ID de la categoría es obligatorio")
    // public SetLong categoryIds;

    @NotNull(message = "Debe especificar al menos una categoría")
    @Size(min = 1, message = "El producto debe tener al menos una categoría")
    public Set<Long> categoryIds; // Múltiples categorías

}
