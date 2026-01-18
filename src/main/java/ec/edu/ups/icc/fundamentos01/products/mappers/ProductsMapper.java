package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.models.Product;

public class ProductsMapper {

    public static Product toModel(int id, String name, Double price, String description, int stock) {
        return new Product(id, name, price, description, stock);
    }

    /**
     * Convierte un DTO de creación a un modelo Product
     */
    public static Product fromCreateDto(CreateProductsDto dto) {
        Product product = new Product(0, dto.name, dto.price, null, dto.stock);
        return product;
    }

    public static Product fromUpdateDto(UpdateProductsDto dto) {
        return new Product(0, dto.name, dto.price, dto.description, dto.stock);
    }

    /**
     * Convierte un Product a un DTO de respuesta
     * 
     * @param product Modelo de dominio
     * @return DTO con los datos públicos del producto
     */
    public static ProductsResponseDto toResponse(Product product) {
        ProductsResponseDto dto = new ProductsResponseDto();
        dto.id = (long) product.getId();
        dto.name = product.getName();
        dto.price = product.getPrice();
        dto.stock = product.getStock();
        return dto;
    }

}