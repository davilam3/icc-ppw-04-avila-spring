package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;

public interface ProductService {

    List<ProductsResponseDto> findAll();

    Object findOne(int id);

    ProductsResponseDto create(CreateProductsDto dto);
    
    Object update(int id, UpdateProductsDto dto);

    Object partialUpdate(int id, PartialUpdateProductsDto dto);

    Object delete(int id);
}