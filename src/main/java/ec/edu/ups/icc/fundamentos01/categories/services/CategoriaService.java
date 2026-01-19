package ec.edu.ups.icc.fundamentos01.categories.services;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoriaDto;

public interface CategoriaService {

    List<CategoriaResponseDto> findAll();

    void save(CreateCategoriaDto dto);

    // public CategoriaResponseDto create(CreateCategoriaDto categoriaDto);

}
