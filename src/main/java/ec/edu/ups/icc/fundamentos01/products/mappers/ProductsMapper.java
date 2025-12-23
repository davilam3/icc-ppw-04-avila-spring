package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Products;

public class ProductsMapper {

   public static Products toEntity(int id, String name, String description, double price) {
        return new Products(id, name, description, price);
    }

    public static ProductsResponseDto toResponse(Products product) {
        ProductsResponseDto dto = new ProductsResponseDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.description = product.getDescription();
        dto.price = product.getPrice();
        return dto;
    }
}
