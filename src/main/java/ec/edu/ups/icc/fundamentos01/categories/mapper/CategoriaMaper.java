package ec.edu.ups.icc.fundamentos01.categories.mapper;

import java.util.ArrayList;
import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;

public class CategoriaMaper {


   public static CategoriaResponseDto tResponseDto(CategoryEntity categoryEntity) {

        List<ProductResponseDto> productsDTO = new ArrayList<>();
        for (var productEntity : categoryEntity.getProduct()) {
            var productDto = new ProductResponseDto();
            productDto.id = productEntity.getId();
            productDto.name = productEntity.getName();
            productDto.description = productEntity.getDescription();
            productDto.price = productEntity.getPrice();
            productsDTO.add(productDto);
        }

        return new CategoriaResponseDto() {
            {
                id = categoryEntity.getId();
                name = categoryEntity.getName();
                description = categoryEntity.getDescription();
                products = productsDTO;
            }

        };
    }
    
}
