package ec.edu.ups.icc.fundamentos01.categories.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.Repository.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.ConflictException;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoryRepository categoryRepository;

    public CategoriaServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoriaResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(entity -> {
                    CategoriaResponseDto dto = new CategoriaResponseDto();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setDescription(entity.getDescription());
                    return dto;
                })
                .toList();
    }


    @Override
    public void save(CreateCategoriaDto categoriaDto) {

        if (categoriaDto.name == null || categoriaDto.name.trim().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoria es obligatorio");
        }

        categoryRepository.findByNameIgnoreCase(categoriaDto.name)
                .ifPresent(existingCategory -> {
                    throw new ConflictException("Ya existe una categoria con el nombre: " + categoriaDto.name);
                });

        CategoryEntity entity = new CategoryEntity();
        entity.setName(categoriaDto.name);
        entity.setDescription(categoriaDto.description);
        categoryRepository.save(entity);

    }

}
