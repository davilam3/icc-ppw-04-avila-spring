package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PartialUpdateProductsDto {

    @Size(min = 3, max = 150)
    public String name;

    @Min(value = 0)
    public Double price;

    @Size(max = 500)
    public String description;

    @NotNull(message = "Debe especificar las categorias")
    @Size(min = 1, message = "Debe ingresar al menos un ID de categoría")
    public List<Long> categoryIds;

}