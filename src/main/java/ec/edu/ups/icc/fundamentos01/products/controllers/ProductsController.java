package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

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
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductsController {

private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductsResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Object findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping
    public ProductsResponseDto create(@RequestBody CreateProductsDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable int id, @RequestBody UpdateProductsDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateProductsDto dto) {
        return service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        return service.delete(id);
    }

    
}


// private List<Products> products = new ArrayList<>();
    // private int currentId = 1;

    // @GetMapping
    // public List<ProductsResponseDto> findAll() {


    //     // Programación funcional para mapear cada User a UserResponseDto
    //     return products.stream()
    //             .map(ProductsMapper::toResponse)
    //             .toList();
    // }

    //  @GetMapping("/{id}")
    // public Object findOne(@PathVariable int id) {

    //     // Programación tradicional iterativa para mapear cada User a UserResponseDto
    //     // Busqueda Lineal
    //     for (Products p : products) {
    //         if (p.getId() == id) {
    //             return ProductsMapper.toResponse(p);
    //         }
    //     }
    //     return new Object() {
    //         public String error = "Product not found";
    //     };


    // }

    // @PostMapping
    // public ProductsResponseDto create(@RequestBody CreateProductsDto dto) {
    //     Products p = ProductsMapper.toEntity(currentId++, dto.name, dto.categoria);
    //     products.add(p);
    //     return ProductsMapper.toResponse(p);
    // }

    // @PutMapping("/{id}")
    // public Object update(@PathVariable int id, @RequestBody UpdateProductsDto dto) {


    //     // Programacion funcional
    //     Products p = products.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    //     if (p == null)
    //         return new Object() {
    //             public String error = "Product not found";
    //         };

    //     p.setName(dto.name);
    //     p.setCategoria(dto.categoria);

    //     return ProductsMapper.toResponse(p);
    // }

    //  @PatchMapping("/{id}")
    // public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateProductsDto dto) {

        
    //     // Programacion funcional
    //     Products p = products.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    //     if (p == null)
    //         return new Object() {
    //             public String error = "Product not found";
    //         };

    //     if (dto.name != null)
    //         p.setName(dto.name);
    //     if (dto.categoria != null)
    //         p.setCategoria(dto.categoria);       
    //     return ProductsMapper.toResponse(p);    
    // }

    // @DeleteMapping("/{id}")
    // public Object delete(@PathVariable int id) {

    //     // Programacion funcional
    //     boolean exists = products.removeIf(p -> p.getId() == id);
    //     if (!exists)
    //         return new Object() {
    //             public String error = "Product not found";
    //         };

    //     return new Object() {
    //         public String message = "Deleted successfully";
    //     };
    // }
