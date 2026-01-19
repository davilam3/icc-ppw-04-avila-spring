package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.SecureUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductsEntity;
import ec.edu.ups.icc.fundamentos01.categories.Repository.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.entity.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.exceptions.base.BusinessException;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductsMapper;
import ec.edu.ups.icc.fundamentos01.products.models.Product;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductsRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public ProductServiceImpl(ProductsRepository productsRepo, UserRepository userRepo,
            CategoryRepository categoryRepo) {
        this.productsRepo = productsRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productsRepo.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findOne(Long id) {
        return productsRepo.findById((long) id)
                .map(Product::fromEntity)
                .map(ProductsMapper::toResponse)
                .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
    }

    @Override
    public ProductResponseDto create(CreateProductsDto dto) {
        // 1. VALIDAR EXISTENCIA DE RELACIONES
        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        // CategoryEntity category = categoryRepo.findById(dto.categoryId)
        // .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " +
        // dto.categoryId));

        // 2. VALIDAR Y OBTENER CATEGORÍAS
        Set<CategoryEntity> categories = validateAndGetCategories(new HashSet<>(dto.categoryIds));

        // Regla: nombre único
        if (productsRepo.findByName(dto.name).isPresent()) {
            throw new IllegalStateException("El nombre del producto ya está registrado");
        }

        // 2. CREAR MODELO DE DOMINIO
        Product product = Product.fromDto(dto);

        // 3. CONVERTIR A ENTIDAD CON RELACIONES
        ProductsEntity entity = product.toEntity(owner);
        // agregar categorías UNA POR UNA
        for (CategoryEntity category : categories) {
            entity.addCategory(category);
        }

        // 4. PERSISTIR
        ProductsEntity saved = productsRepo.save(entity);

        // 5. CONVERTIR A DTO DE RESPUESTA
        return toResponseDto(saved);
    }

    @Override
    public ProductResponseDto update(Long id, UpdateProductsDto dto) {
        // 1. BUSCAR PRODUCTO EXISTENTE
        ProductsEntity existing = productsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // 2. VALIDAR NUEVA CATEGORÍA (si cambió)
        // CategoryEntity newCategory = categoryRepo.findById(dto.categoryId)
        // .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " +
        // dto.categoryId));

        Set<CategoryEntity> categories = validateAndGetCategories(new HashSet<>(dto.categoryIds));

        // 3. ACTUALIZAR USANDO DOMINIO
        Product product = Product.fromEntity(existing);
        product.update(dto);

        // 4. CONVERTIR A ENTIDAD MANTENIENDO OWNER ORIGINAL
        ProductsEntity updated = product.toEntity(existing.getOwner());
        updated.setId(id); // Asegurar que mantiene el ID

        updated.clearCategories();
        for (CategoryEntity category : categories) {
            updated.addCategory(category);
        }

        // 5. PERSISTIR Y RESPONDER
        ProductsEntity saved = productsRepo.save(updated);
        return toResponseDto(saved);
    }

    @Override
    public List<ProductResponseDto> findByUserId(Long userId) {

        //Validar que el usuario existe
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException("Usuario no encontrado con ID: " + userId);
        }

        return productsRepo.findByOwnerId(userId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> findByCategoryId(Long categoryId) {

        // // Validar que la categoría existe
        // if (!categoryRepo.existsById(categoryId)) {
        // throw new NotFoundException("Categoría no encontrada con ID: " + categoryId);
        // }

        // return productsRepo.findByCategoryId(categoryId)
        // .stream()
        // .map(this::toResponseDto)
        // .toList();
        return null;
    }

    @Override
    public ProductResponseDto partialUpdate(Long id, PartialUpdateProductsDto dto) {

        // 1. BUSCAR PRODUCTO EXISTENTE
        ProductsEntity existing = productsRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con ID: " + id));

        // 2. VALIDAR NUEVA CATEGORÍA
        Set<CategoryEntity> categoriaEntities = validateAndGetCategories(new HashSet<>(dto.categoryIds));

        // 3. ACTUALIZAR USANDO Instancia de entidad
        existing.setDescription(dto.description != null ? dto.description : existing.getDescription());
        existing.setName(dto.name != null ? dto.name : existing.getName());
        existing.setPrice(dto.price != null ? dto.price : existing.getPrice());

        existing.clearCategories();

        existing.setCategories(categoriaEntities);
        // 5. PERSISTIR Y RESPONDER
        ProductsEntity saved = productsRepo.save(existing);

        return toResponseDto(saved);
    }

    @Override
    public void delete(Long id) {

        // Verifica existencia y elimina
        productsRepo.findById((long) id)
                .ifPresentOrElse(
                        productsRepo::delete,
                        () -> {
                            throw new IllegalStateException("Producto no encontrado");
                        });
    }

    @Override
    public boolean validateName(Long id, String name) {
        productsRepo.findByName(name)
                .ifPresent(existing -> {
                    if (id == null || existing.getId() != id.longValue()) {
                        throw new ConflictException(
                                "Ya existe un producto con el nombre '" + name + "'");
                    }
                });
        return true;
    }

    @Override
    public ProductResponseDto secureUpdate(int id, SecureUpdateProductDto dto) {

        UserEntity owner = userRepo.findById(dto.userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + dto.userId));

        // CategoryEntity category = categoryRepo.findById(dto.categoryId)
        // .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID: " +
        // dto.categoryId));

        Set<CategoryEntity> categories = validateAndGetCategories(new HashSet<>(dto.categoryIds));

        ProductsEntity entity = productsRepo.findById((long) id)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        if (dto.price != null && dto.price > 1000) {
            if (dto.reason == null || dto.reason.isBlank()) {
                throw new BusinessException(
                        "Productos con precio mayor a 1000 requieren justificación");
            }
        }

        Product product = Product.fromEntity(entity);

        if (dto.name != null)
            product.setName(dto.name);
        if (dto.price != null)
            product.setPrice(dto.price);
        if (dto.description != null)
            product.setDescription(dto.description);

        ProductsEntity saved = productsRepo.save(product.toEntity(owner));

        return ProductsMapper.toResponse(Product.fromEntity(saved));
    }

    /**
     * Convierte ProductEntity a ProductResponseDto
     * Usa estructura anidada para mejor organización semántica
     */
    private ProductResponseDto toResponseDto(ProductsEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        // Campos básicos del producto
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();

        // Crear objeto User anidado (se carga LAZY)
        ProductResponseDto.UserSummaryDto userDto = new ProductResponseDto.UserSummaryDto();
        userDto.id = entity.getOwner().getId();
        userDto.name = entity.getOwner().getName();
        userDto.email = entity.getOwner().getEmail();
        dto.user = userDto;

        // Crear objeto Category anidado (se carga LAZY)

        List<CategoriaResponseDto> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : entity.getCategories()) {
            CategoriaResponseDto categoryDto = new CategoriaResponseDto();
            categoryDto.id = categoryEntity.getId();
            categoryDto.name = categoryEntity.getName();
            categoryDto.description = categoryEntity.getDescription();
            
            categories.add(categoryDto);
        }

        dto.categories = categories;
        // Auditoría
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        return dto;
    }

    private Set<CategoryEntity> validateAndGetCategories(Set<Long> categoryIds) {

        // Set<CategoryEntity> categories = new HashSet<>();

        // for (Long categoryId : categoryIds) {
        // CategoryEntity category = categoryRepo.findById(categoryId)
        // .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " +
        // categoryId));
        // categories.add(category);
        // }

        // return categories;

        Set<CategoryEntity> categories = new HashSet<>(categoryRepo.findAllById(categoryIds));

        if (categories.size() != categoryIds.size()) {
            throw new NotFoundException("Una o más categorías no existen");
        }

        return categories;
    }

}

// public ProductServiceImpl(ProductsRepository productsRepo, UserRepository
// userRepo,
// CategoryRepository categoryRepo) {

// this.productsRepo = productsRepo;
// this.userRepo = userRepo;
// this.categoryRepo = categoryRepo;
// }

// // Forma iterativa tradicional
// @Override
// public List<ProductsResponseDto> findAll() {

// // Lista final que se devolverá al controlador
// List<ProductsResponseDto> response = new ArrayList<>();

// // 1. Obtener todas las entidades desde la base de datos
// List<ProductsEntity> entities = productsRepo.findAll();

// // 2. Iterar sobre cada entidad
// for (ProductsEntity entity : entities) {

// // 3. Convertir la entidad en modelo de dominio
// Product product = Product.fromEntity(entity);

// // 4. Convertir el modelo de dominio en DTO de respuesta
// ProductsResponseDto dto = product.toResponseDto();

// // 5. Agregar el DTO a la lista de resultados
// response.add(dto);
// }

// // 6. Retornar la lista final de DTOs
// return response;
// }

// @Override
// public ProductsResponseDto findOne(long id) {
// return productsRepo.findById(id)
// .map(Product::fromEntity)
// .map(Product::toResponseDto)
// .orElseThrow(() -> new NotFoundException("Producto con id: " + id + " no
// encontrado"));
// }

// private ProductsResponseDto toResponseDto(ProductsEntity entity) {
// ProductsResponseDto dto = new ProductsResponseDto();
// dto.id = entity.getId();
// dto.name = entity.getName();
// dto.price = entity.getPrice();
// dto.stock = entity.getStock();

// ProductsResponseDto.UserSummaryDto userDto = new
// ProductsResponseDto.UserSummaryDto();
// userDto.id = entity.getOwner().getId().intValue();
// userDto.username = entity.getOwner().getName();

// CategoriaResponseDto categoryDto = new CategoriaResponseDto();
// categoryDto.id = (long) entity.getCategory().getId().intValue();
// categoryDto.name = entity.getCategory().getName();

// dto.userId = userDto;
// dto.categoryId = (List<CategoriaResponseDto>) categoryDto;
// return dto;

// }

// @Override
// public ProductsResponseDto update(long id, UpdateProductsDto dto) {

// return productsRepo.findById((long) id)
// // Entity → Domain
// .map(Product::fromEntity)
// // Aplicar cambios permitidos en el dominio
// .map(product -> product.update(dto))

// // Domain → Entity
// .map(Product::toEntity)

// // Persistencia
// .map(productsRepo::save)
// // Entity → Domain
// .map(Product::fromEntity)

// // Domain → DTO
// .map(Product::toResponseDto)

// // Error controlado si no existe
// .orElseThrow(() -> new NotFoundException("Producto con id: " + id + " no
// encontrado"));
// }

// @Override
// public ProductsResponseDto partialUpdate(long id, PartialUpdateProductsDto
// dto) {
// return productsRepo.findById((long) id)
// // Entity → Domain
// .map(Product::fromEntity)
// // Aplicar solo los cambios presentes
// .map(product -> product.partialUpdate(dto))

// // Domain → Entity
// .map(Product::toEntity)

// // Persistencia
// .map(productsRepo::save)
// // Entity → Domain
// .map(Product::fromEntity)

// // Domain → DTO
// .map(Product::toResponseDto)

// // Error si no existe
// .orElseThrow(() -> new NotFoundException("Producto con id: " + id + " no
// encontrado"));
// }

// @Override
// public void delete(long id) {
// // Verifica existencia y elimina
// productsRepo.findById(id)
// .ifPresentOrElse(
// productsRepo::delete,
// () -> {
// throw new NotFoundException("Producto con id: " + id + " no encontrado");
// });
// }

// @Override
// public boolean validateName(Long id, String name) {
// productsRepo.findByName(name)
// .ifPresent(existing -> {
// if (id == null || existing.getId() != id.longValue()) {
// throw new ConflictException("El nombre: '" + name + "' ya está registrado");
// }
// });
// return true;
// }

// @Override
// public ProductsResponseDto secureUpdate(long id, SecureUpdateProductDto dto)
// {
// ProductsEntity entity = productsRepo.findById(id)
// .orElseThrow(() -> new BusinessException("Producto no encontrado"));

// if (dto.price != null && dto.price > 1000) {
// if (dto.reason == null || dto.reason.isBlank()) {
// throw new BusinessException(
// "Productos con precio mayor a 1000 requieren justificación");
// }
// }

// Product product = Product.fromEntity(entity);

// if (dto.name != null)
// product.setName(dto.name);
// if (dto.price != null)
// product.setPrice(BigDecimal.valueOf(dto.price));

// ProductsEntity saved = productsRepo.save(product.toEntity());

// return ProductsMapper.toResponse(Product.fromEntity(saved));
// }

// @Override
// public ProductsResponseDto create(CreateProductsDto dto) {

// UserEntity user = userRepo.findById(dto.getUserId())
// .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

// CategoryEntity category = categoryRepo.findById(dto.getCategoriesId())
// .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

// ProductsEntity entity = new ProductsEntity();
// entity.setName(dto.getName());
// entity.setPrice(dto.getPrice());
// entity.setStock(dto.getStock());
// entity.setOwner(user);
// entity.setCategory(category);

// return toResponseDto(productsRepo.save(entity));
// }

// @Override
// public ProductsResponseDto findById(Long id) {
// return productsRepo.findById(id)
// .map(this::toResponseDto)
// .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
// }

// @Override
// public List<ProductsResponseDto> findByUserId(Long userId) {
// return productsRepo.findByOwnerId(userId)
// .stream()
// .map(this::toResponseDto)
// .toList();
// }

// @Override
// public List<ProductsResponseDto> findByCategoryId(Long categoryId) {
// return productsRepo.findByCategoriesId(categoryId)
// .stream()
// .map(this::toResponseDto)
// .toList();
// }

// private List<CategoryEntity> getCategoriesByIds(List<Long> categoryIds) {
// for (Long categoryId : categoryIds) {
// categoryRepo.findById(categoryId)
// .orElseThrow(() -> new NotFoundException("Categoría con id: " + categoryId +
// " no encontrada"));
// }
// return categoryRepo.findAllById(categoryIds);

// }
