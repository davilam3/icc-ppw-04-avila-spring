package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;

public interface ProductService {

    List<ProductsResponseDto> findAll();

    ProductsResponseDto findOne(int id);

    ProductsResponseDto create(CreateProductsDto dto);

    ProductsResponseDto update(int id, UpdateProductsDto dto);

    ProductsResponseDto partialUpdate(int id, PartialUpdateProductsDto dto);

    void delete(int id);
}