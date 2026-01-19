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

    private final CategoryRepository repository;

    public CategoriaServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoriaResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(entity -> {
                    CategoriaResponseDto dto = new CategoriaResponseDto();
                    dto.id = entity.getId();
                    dto.name = entity.getName();
                    dto.description = entity.getDescription();
                    return dto;
                })
                .toList();
    }

    @Override
    public void save(CreateCategoriaDto dto) {
        // Valida que el nombre no sea nulo
        if (dto.name == null || dto.name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío");
        }

        // Valida que no exista una categoria con el mismo nombre
        repository.findByNameIgnoreCase(dto.name)
                .ifPresent(existingCategory -> {
                    throw new ConflictException("Ya existe esa categoria" + dto.name);
                });

        // Crear la entidad de categoria

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.name);
        entity.setDescription(dto.description);

        // Guardar la entidad en la base de datos
        repository.save(entity);
    }

}
