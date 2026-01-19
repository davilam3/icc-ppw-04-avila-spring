package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

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
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        List<ProductResponseDto> products = productService.findAll();
        return (List<ProductResponseDto>) ResponseEntity.ok(products);
        // return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable("id") Long id) {
        ProductResponseDto product = productService.findOne(id);
        return ResponseEntity.ok(product);
        // return productService.findOne(id);
    }

    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody CreateProductsDto dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable("id") Long id,
            @Valid @RequestBody UpdateProductsDto dto) {
        ProductResponseDto updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
        // return productService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public ProductResponseDto partialUpdate(@PathVariable("id") Long id,
            @Valid @RequestBody PartialUpdateProductsDto dto) {
        return productService.partialUpdate(id, dto);
    }

    // @PostMapping("/validate-name")
    // public ResponseEntity<Boolean> validateName(@RequestBody ValidateProductsDto
    // dto) {
    // productService.validateName(dto.id, dto.name);
    // return ResponseEntity.ok(true);
    // }

    // @PutMapping("/{id}/secure-update")
    // public ProductResponseDto secureUpdate(@PathVariable("id") int id,
    // @RequestBody SecureUpdateProductDto dto) {
    // return productService.secureUpdate(id, dto);
    // }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponseDto>> findByUserId(@PathVariable("userId") Long userId) {
        List<ProductResponseDto> products = productService.findByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> findByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<ProductResponseDto> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
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
