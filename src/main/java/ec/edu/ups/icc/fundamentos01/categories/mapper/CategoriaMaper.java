package ec.edu.ups.icc.fundamentos01.categories.mapper;

import ec.edu.ups.icc.fundamentos01.categories.Model.Category;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;

public class CategoriaMaper {


    public static Category toEntity(Long id, String name, String description) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);
        return category;
    }   

    public static CategoriaResponseDto toDto(Category category) {
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
    
    
}
