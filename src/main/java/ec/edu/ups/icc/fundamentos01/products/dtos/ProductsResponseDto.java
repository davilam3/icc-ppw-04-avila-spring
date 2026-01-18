package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;

public class ProductsResponseDto {

    public Long id;
    public String name;
    public String description;
    public Double price;
    public Integer stock;

    // ============== OBJETOS ANIDADOS ==============

    public UserSummaryDto userId;

    // public CategoryResponseDto category;
    // ============== CATEGORÍAS (N:N) - Lista de objetos ==============
    public List<CategoriaResponseDto> categoryIds;

    // ============== AUDITORÍA =============

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    // ============== DTOs INTERNOS ==============

    public static class UserSummaryDto {
        public long id;
        public String name;
        public String email;
    }

}