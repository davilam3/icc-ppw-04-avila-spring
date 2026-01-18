package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ValidateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductsResponseDto> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductsResponseDto findById(@PathVariable("id") Long id) {
        return productService.findOne(id);
    }

    @PostMapping
    public ProductsResponseDto create(@Valid @RequestBody CreateProductsDto dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductsResponseDto update(@PathVariable("id") Long id,
            @Valid @RequestBody UpdateProductsDto dto) {
        return productService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public ProductsResponseDto partialUpdate(@PathVariable("id") Long id,
            @Valid @RequestBody PartialUpdateProductsDto dto) {
        return productService.partialUpdate(id, dto);
    }

    // @PutMapping("/{id}/secure-update")
    // public ProductsResponseDto secureUpdate(@PathVariable("id") int id,
    // @RequestBody SecureUpdateProductDto dto ){
    // return productService.secureUpdate(id, dto);
    // }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @PostMapping("validate-name")
    public ResponseEntity<Boolean> validateName(@RequestBody ValidateProductsDto dto) {

        productService.validateName(dto.id, dto.name);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductsResponseDto>> findByUserId(@PathVariable Long userId) {
        List<ProductsResponseDto> products = productService.findByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductsResponseDto>> findByCategoryId(@PathVariable Long categoryId) {
        List<ProductsResponseDto> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    // @PostMapping
    // public ResponseEntity<ProductsResponseDto> create(
    // @Valid @RequestBody CreateProductsDto dto) {
    // ProductsResponseDto created = productService.create(dto);
    // return ResponseEntity.status(HttpStatus.CREATED).body(created);
    // }

    // @PatchMapping("/{id}")
    // public ProductsResponseDto partialUpdate(@PathVariable("id") Long id,
    // @RequestBody PartialUpdateProductsDto dto) {
    // return productService.partialUpdate(id, dto);
    // }

    // 16 enero
    // @GetMapping("/{id}")
    // public ResponseEntity<ProductsResponseDto> findById(@PathVariable Long id) {
    // ProductsResponseDto product = productService.findById(id);
    // return ResponseEntity.ok(product);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<ProductsResponseDto> update(
    // @PathVariable Long id,
    // @Valid @RequestBody UpdateProductsDto dto
    // ) {
    // ProductsResponseDto updated = productService.update(id, dto);
    // return ResponseEntity.ok(updated);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable Long id) {
    // productService.delete(id);
    // return ResponseEntity.noContent().build();
    // }

}
