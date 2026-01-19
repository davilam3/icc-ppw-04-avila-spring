package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductsEntity;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductsMapper;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductsRepository;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.models.User;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ProductsRepository productRepository;

    public UserServiceImpl(UserRepository userRepo, ProductsRepository productRepository) {
        this.productRepository = productRepository;
        this.userRepo = userRepo;
    }

    // Forma iterativa tradicional
    @Override
    public List<UserResponseDto> findAll() {
        // Lista final que se devolverá al controlador
        List<UserResponseDto> response = new ArrayList<>();

        // 1. Obtener todas las entidades desde la base de datos
        List<UserEntity> entities = userRepo.findAll();

        // 2. Iterar sobre cada entidad
        for (UserEntity entity : entities) {

            // 3. Convertir la entidad en modelo de dominio
            User user = User.fromEntity(entity);

            // 4. Convertir el modelo de dominio en DTO de respuesta
            UserResponseDto dto = UserMapper.toResponse(user);

            // 5. Agregar el DTO a la lista de resultados
            response.add(dto);
        }

        // 6. Retornar la lista final de DTOs
        return response;
    }

    @Override
    public UserResponseDto findOne(Long id) {
        return userRepo.findById(id)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Usuario con id: " + id + " no encontrado"));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        // Validar que el email no exista ya ANTES de intentar insertar
        if (userRepo.findByEmail(dto.email()).isPresent()) {
            throw new ConflictException("El email '" + dto.email() + "' ya está registrado");
        }

        return Optional.of(dto)
                .map(UserMapper::fromCreateDto)
                .map(User::toEntity)
                .map(userRepo::save)
                .map(User::fromEntity)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new ConflictException("Error al crear el usuario" + dto));

        // User user = User.fromDto(dto);

        // UserEntity saved = userRepo.save(user.toEntity());

        // return UserMapper.toResponse(User.fromEntity(saved));
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        return userRepo.findById((long) id)
                // Entity → Domain
                .map(User::fromEntity)
                // Aplicar cambios permitidos en el dominio
                .map(user -> user.update(dto))
                // Domain → Entity
                .map(User::toEntity)
                // Persistencia
                .map(userRepo::save)
                // Entity → Domain
                .map(User::fromEntity)

                // Domain → DTO
                .map(UserMapper::toResponse)

                // Error controlado si no existe
                .orElseThrow(() -> new NotFoundException("Usuario con id: " + id + " no encontrado"));
    }

    @Override
    public UserResponseDto partialUpdate(Long id, PartialUpdateUserDto dto) {

        return userRepo.findById((long) id)
                // Entity → Domain
                .map(User::fromEntity)

                // Aplicar solo los cambios presentes
                .map(user -> user.partialUpdate(dto))

                // Domain → Entity
                .map(User::toEntity)

                // Persistencia
                .map(userRepo::save)

                // Entity → Domain
                .map(User::fromEntity)

                // Domain → DTO
                .map(UserMapper::toResponse)

                // Error si no existe
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public void delete(Long id) {
        // Verifica existencia y elimina
        userRepo.findById((long) id)
                .ifPresentOrElse(
                        userRepo::delete,
                        () -> {
                            throw new NotFoundException("Usuario con id: " + id + " no encontrado");
                        });
    }

    @Override
    public List<ProductResponseDto> getProductsByUserId(Long userId) {

        // 1. Verificar que el usuario exista
        userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "Usuario con id: " + userId + " no encontrado"));

        // 2. Consultar productos desde el repositorio de productos
        return productRepository.findByOwnerId(userId)
                .stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDescription()
                // product.getCategoris()
                ))
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductsByUserIdWithFilters(
            Long userId,
            String name,
            Double minPrice,
            Double maxPrice,
            Long categoryId) {

        List<ProductsEntity> products = productRepository.findByOwnerWithFilter(
                userId,
                name,
                minPrice,
                maxPrice,
                categoryId);

        return products.stream()
                .map(this::toResponseDto)
                .toList();
    }

    private ProductResponseDto toResponseDto(ProductsEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();

        // User
        ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
        userDto.id = entity.getOwner().getId();
        userDto.name = entity.getOwner().getName();
        userDto.email = entity.getOwner().getEmail();
        dto.user = userDto;

        // Categories
        List<CategoriaResponseDto> categories = new ArrayList<>();
        for (CategoryEntity c : entity.getCategories()) {
            CategoriaResponseDto catDto = new CategoriaResponseDto();
            catDto.id = c.getId();
            catDto.name = c.getName();
            catDto.description = c.getDescription();
            categories.add(catDto);
        }
        dto.categories = categories;

        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        return dto;
    }

}