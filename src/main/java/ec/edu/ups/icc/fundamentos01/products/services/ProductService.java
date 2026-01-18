package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.SecureUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;

public interface ProductService {

    List<ProductsResponseDto> findAll();

    ProductsResponseDto findOne(Long id);

    ProductsResponseDto create(CreateProductsDto dto);

    ProductsResponseDto update(Long id, UpdateProductsDto dto);

    ProductsResponseDto partialUpdate(Long id, PartialUpdateProductsDto dto);

    void delete(Long id);

    boolean validateName(Integer id, String name);

    // ProductsResponseDto findById(Long id);

    ProductsResponseDto secureUpdate(Long id, SecureUpdateProductDto dto);

    List<ProductsResponseDto> findByUserId(Long userId);

    List<ProductsResponseDto> findByCategoryId(Long categoryId);


}