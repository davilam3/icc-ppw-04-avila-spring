package ec.edu.ups.icc.fundamentos01.products.models;


import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductsEntity;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private int stock;
    private String createdAt;

    // Constructor privado para forzar uso de factory methods
    public Product(long id, String name, Double price, String description, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    public Product(String name, Double price, String description,int stock) {
        this.validateBusinessRules(name, price, description);
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    private void validateBusinessRules(String name, Double price, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede superar 500 caracteres");
        }
    }

    // ==================== FACTORY METHODS ====================

    public ProductsEntity toEntity(UserEntity owner) {
        ProductsEntity entity = new ProductsEntity();
        if (this.id != null && this.id > 0) {
            entity.setId(this.id);
        }

        entity.setName(this.name);
        entity.setPrice(this.price);
        entity.setStock(this.stock);
        entity.setDescription(this.description);

        // Asignar relaciones
        entity.setOwner(owner);

        return entity;
    }

    public Product update(UpdateProductsDto dto) {
        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
        this.description = dto.description;
        return this;
    }

    public Product update(PartialUpdateProductsDto dto) {
        this.name = dto.name;
        this.price = dto.price;
        this.stock = dto.stock;
        this.description = dto.description;
        return this;
    }

    /**
     * Crea un Product desde un DTO de creación
     */
    public static Product fromDto(CreateProductsDto dto) {
        return new Product(dto.name, dto.price, dto.description, dto.stock);
    }

    /**
     * Crea un Product desde una entidad persistente
     */
    public static Product fromEntity(ProductsEntity entity) {
        Product product = new Product(
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getStock());
        product.id = entity.getId();
        return product;
    }

    public long getId() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    

    public Product partialUpdate(PartialUpdateProductsDto dto) {

        if (dto.name != null) {
            this.name = dto.name;
        }

        if (dto.price != null) {
            this.price = dto.price;
        }

        if (dto.stock != null) {
            this.stock = dto.stock;
        }

        if (dto.description != null) {
            this.description = dto.description;
        }

        return this;
    }


}


// // ==================== FACTORY METHODS ====================

// /**
// * Crea un Product desde un DTO de creación
// * @param dto DTO con datos del formulario
// * @return instancia de Product para lógica de negocio
// */

// public static Product fromDto(CreateProductDto dto) {
// return new Product(dto.name, dto.price, dto.description);
// }

// /**
// * Crea un Product desde una entidad persistente
// * @param entity Entidad recuperada de la BD
// * @return instancia de Product para lógica de negocio
// */
// public static Product fromEntity(ProductEntity entity) {
// Product product = new Product(
// entity.getName(),
// entity.getPrice(),
// entity.getDescription()
// );
// product.id = entity.getId();
// return product;
// }

// public ProductEntity toEntity(UserEntity owner, CategoriaEntity category) {
// ProductEntity entity = new ProductEntity();

// if (this.id != null && this.id > 0) {
// entity.setId(this.id);
// }

// entity.setName(this.name);
// entity.setPrice(this.price);
// entity.setDescription(this.description);

// // Asignar relaciones
// entity.setOwner(owner);
// entity.setCategory(category);

// return entity;
// }

// // ==================== CONVERSION METHODS ====================

// /**
// * Convierte este Product a una entidad persistente
// * @return ProductEntity lista para guardar en BD
// */
// public ProductsEntity toEntity() {
// ProductsEntity entity = new ProductsEntity();

// // Si ya tiene id, lo asignamos (para updates)
// if (this.id != null && this.id > 0) {
// entity.setId(this.id);
// }

// entity.setName(this.name);
// entity.setDescription(this.description);
// entity.setPrice(this.price);
// entity.setStock(this.stock);
// return entity;
// }

// /**
// * Convierte este Product a un DTO de respuesta
// * @return DTO sin información sensible
// */
// public ProductsResponseDto toResponseDto() {
// ProductsResponseDto dto = new ProductsResponseDto();
// dto.setId(this.id);
// dto.setName(this.name);
// dto.setDescription(this.description);
// dto.setPrice(this.price);
// dto.setStock(this.stock);
// if (this.createdAt != null) {
// dto.setCreatedAt(this.createdAt.toString());
// }
// return dto;
// }

// // ==================== UPDATE METHODS ====================

// /**
// * Actualiza todos los campos del producto
// * @param dto DTO con los nuevos datos
// * @return this para encadenamiento
// */
// public Product update(UpdateProductsDto dto) {
// if (dto.name == null || dto.name.isBlank())
// throw new IllegalArgumentException("El nombre es obligatorio");
// if (dto.price < 0)
// throw new IllegalArgumentException("El precio no puede ser negativo");
// if (dto.stock < 0)
// throw new IllegalArgumentException("El stock no puede ser negativo");

// this.name = dto.name;
// this.description = dto.description;
// this.price = dto.price;
// this.stock = dto.stock;
// return this;
// }

// /**
// * Actualiza solo los campos proporcionados
// * @param dto DTO con los campos a actualizar (opcionales)
// * @return this para encadenamiento
// */
// public Product partialUpdate(PartialUpdateProductsDto dto) {
// if (dto.name != null) {
// if (dto.name.isBlank())
// throw new IllegalArgumentException("El nombre no puede estar vacío");
// this.name = dto.name;
// }
// if (dto.description != null) {
// this.description = dto.description;
// }
// if (dto.price != null) {
// if (dto.price < 0)
// throw new IllegalArgumentException("El precio no puede ser negativo");
// this.price = dto.price;
// }
// if (dto.stock != null) {
// if (dto.stock < 0)
// throw new IllegalArgumentException("El stock no puede ser negativo");
// this.stock = dto.stock;
// }
// return this;
// }
// }

// /**
// * Constructor básico para nuevos productos
// */

// public static Product fromDto(CreateProductsDto dto) {
// return new Product(
// dto.name,
// dto.price,
// dto.stock);
// }

// public Product(String name, BigDecimal price, Integer stock) {

// if (name == null || name.isBlank())
// throw new BadRequestException("Nombre inválido");
// if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
// throw new BadRequestException("Precio inválido");
// if (stock == null || stock < 0)
// throw new BadRequestException("Stock inválido");

// this.name = name;
// this.price = price;
// this.stock = stock;
// this.createdAt = LocalDateTime.now();
// }

// public Product(long id, String name, BigDecimal price, Integer stock) {

// if (name == null || name.isBlank())
// throw new BadRequestException("Nombre inválido");
// if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
// throw new BadRequestException("Precio inválido");
// if (stock == null || stock < 0)
// throw new BadRequestException("Stock inválido");

// this.id = id;
// this.name = name;
// this.price = price;
// this.stock = stock;
// this.createdAt = LocalDateTime.now();
// }

// /**
// * Constructor completo incluyendo fechas (para conversiones desde entidad)
// */
// public Product(long id, String name, BigDecimal price, Integer stock,
// LocalDateTime createdAt) {
// this.id = id;
// this.name = name;
// this.price = price;
// this.stock = stock;
// this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
// }

// /**
// * Crea un Product desde una entidad persistente
// *
// * @param entity Entidad recuperada de la BD
// * @return instancia de Product para lógica de negocio
// */

// public static Product fromEntity(ProductsEntity entity) {
// return new Product(
// entity.getId().intValue(),
// entity.getName(),
// entity.getPrice(),
// entity.getStock(),
// entity.getCreatedAt());
// }

// /**
// * Convierte este Product a una entidad persistente
// *
// * @return ProductsEntity lista para guardar en BD
// */

// public ProductsEntity toEntity(UserEntity owner, CategoryEntity
// categoryEntity){
// ProductsEntity entity = new ProductsEntity();
// if (this.id > 0) {
// entity.setId(this.id);
// }
// entity.setName(this.name);
// entity.setPrice(this.price);
// entity.setStock(this.stock);

// entity.setOwner(owner);
// entity.setCategory(categoryEntity);

// return entity;
// }

// public ProductsEntity toEntity() {
// ProductsEntity entity = new ProductsEntity();
// if (this.id > 0) {
// entity.setId((long) this.id);
// }
// entity.setName(this.name);
// entity.setPrice(this.price);
// entity.setStock(this.stock);

// // Preservar fechas si existen (para actualizaciones)
// if (this.createdAt != null) {
// entity.setCreatedAt(this.createdAt);
// }

// return entity;
// }

// public ProductsResponseDto toResponseDto() {
// ProductsResponseDto dto = new ProductsResponseDto();
// dto.id = this.id;
// dto.name = this.name;
// dto.price = this.price;
// dto.stock = this.stock;
// dto.createdAt = this.createdAt.toString();
// return dto;
// }

// public Product update(UpdateProductsDto dto) {
// this.name = dto.name;
// this.price = dto.price;
// this.stock = dto.stock;
// return this;
// }

// public Product partialUpdate(PartialUpdateProductsDto dto) {
// if (dto.name != null) {
// this.name = dto.name;
// }

// if (dto.price != null) {
// this.price = dto.price;
// }

// if (dto.stock != null) {
// this.stock = dto.stock;
// }

// return this;
// }

