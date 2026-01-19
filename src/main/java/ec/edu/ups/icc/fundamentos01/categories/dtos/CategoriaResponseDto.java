package ec.edu.ups.icc.fundamentos01.categories.dtos;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;

public class CategoriaResponseDto {
    public Long id;
    public String name;
    public String description;

    public List<ProductResponseDto> products;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}