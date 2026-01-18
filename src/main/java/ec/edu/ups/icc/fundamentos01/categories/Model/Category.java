package ec.edu.ups.icc.fundamentos01.categories.Model;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;

public class Category {
    private Long id;
    private String name;
    private String description;

    public Category() {

    }

    public Category(String name, String description){
        this.name=name;
        this.description=description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
// Metodos Factory paras la factorias
    public static Category fromCategory(CreateCategoriaDto dto){
        return new Category(dto.name, dto.description);
    }

    public static Category fromEntity( CategoryEntity entity){
        Category categoria= new Category(entity.getName(), entity.getDescription());
        categoria.id = entity.getId();
        return categoria;
    }


    public CategoryEntity toEntity(){
        CategoryEntity entity= new CategoryEntity();

        if (this.id != null && this.id>0){
            entity.setId(id);
        }
        entity.setName(name);
        entity.setDescription(description);

        return entity;
    }


    public CategoriaResponseDto tResponseDto(){
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);

        return dto;
    }

    
}
