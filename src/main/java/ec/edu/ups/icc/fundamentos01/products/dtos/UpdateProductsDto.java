package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProductsDto {

    @NotBlank
    @Size(min = 3, max = 150)
    public String name;

    @NotBlank
    @Min(value = 0)
    public Double price;

    @NotBlank
    @Min(value = 0)
    public Integer stock;

    @Size(max = 250)
    public String description;

    // m:m
    @NotBlank(message = "Debe especificar al menos una categoría")
    @Size(min = 1, message = "El producto debe tener al menos una categoría")
    public Set<Long> categoryIds;
    
    //NO PUEDO ACTULIZAR EL DUEÑO DEL PRODUCTO
    //  public Long userId;

}