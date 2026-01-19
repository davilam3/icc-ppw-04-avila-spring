package ec.edu.ups.icc.fundamentos01.categories.controller;

import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDto;
import ec.edu.ups.icc.fundamentos01.categories.services.CategoriaService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/categories")
public class CategoriaControllers {

    private final CategoriaService categoriaService;

    public CategoriaControllers(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // CREAR CATEGORIA
    @PostMapping()
    public ResponseEntity<String> save(@Valid @RequestBody CreateCategoriaDto categoriaDto) {
        categoriaService.save(categoriaDto);
        return ResponseEntity.ok("Categoría creada");
    }

    // LISTAR CATEGORIAS
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<CategoriaResponseDto> findById(@PathVariable Long id) {
    //     CategoriaResponseDto response = categoriaService.findById(id);
    //     return ResponseEntity.ok(response);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<CategoriaResponseDto> update(
    //         @PathVariable Long id,
    //         @Valid @RequestBody UpdateCategoryDto dto) {
    //     CategoriaResponseDto response = categoryService.update(id, dto);
    //     return ResponseEntity.ok(response);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable Long id) {
    //     categoryService.delete(id);
    //     return ResponseEntity.noContent().build();
    // }

    // @GetMapping("/{id}/products/count")
    // public ResponseEntity<Long> countProductsByCategory(@PathVariable Long id) {
    //     CategoryResponseDto category = categoryService.findById(id);
    //     return ResponseEntity.ok((long) category.productCount);
    // }

}
