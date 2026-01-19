package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateProductsDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150)
    public String name;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false)
    public Double price;

    @Size(max = 500)
    public String description;

    public Long categoryId;

    // ============== ACTUALIZACIÓN DE RELACIONES ==============

    // @NotNull(message = "El ID de la categoría es obligatorio")
    // public Long categoryId;

    @NotNull(message = "El ID de la categoría es obligatorio")
    @Size(min = 1, message = "Debe ingresar al menos un ID de categoría")
    public List<Long> categoryIds; // Ingresemos un listado de IDs de categorias [4-2]

    // Nota: No se permite cambiar el owner de un producto una vez creado
    // Si fuera necesario, sería una operación de negocio especial

}