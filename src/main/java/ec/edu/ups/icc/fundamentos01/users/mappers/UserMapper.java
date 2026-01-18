package ec.edu.ups.icc.fundamentos01.users.mappers;


import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.models.User;

public class UserMapper {

    /**
     * Convierte un User a un DTO de respuesta
     * 
     * @param user Modelo de dominio
     * @return DTO con los datos públicos del usuario
     */
    public static UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.createdAt = user.getCreatedAt().toString();
        return dto;
    }

    /**
     * Convierte un DTO de creación a un User
     * 
     * @param dto DTO del formulario de creación
     * @return instancia de User
     */
    public static User fromCreateDto(CreateUserDto dto) {
        User user = new User(0, dto.name, dto.email, dto.password);
        return user;
    }

    // public static ProductsResponseDto toResponse(ProductsEntity entity) {

    //     ProductsResponseDto dto = new ProductsResponseDto();

    //     dto.id = entity.getId();
    //     dto.name = entity.getName();
    //     dto.price = entity.getPrice();
    //     dto.description = entity.getDescription();
    //     dto.stock = entity.getStock();

    //     // User
    //     ProductsResponseDto.UserSummaryDto userDto = new ProductsResponseDto.UserSummaryDto();
    //     userDto.id = entity.getOwner().getId();
    //     userDto.name = entity.getOwner().getName();
    //     userDto.email = entity.getOwner().getEmail();
    //     dto.userId = userDto;

    //     // Category (ONE TO MANY → UNA sola)
    //     CategoriaResponseDto categoryDto = new CategoriaResponseDto();
    //     categoryDto.id = entity.getCategory().getId();
    //     categoryDto.name = entity.getCategory().getName();
    //     categoryDto.description = entity.getCategory().getDescription();
    //     dto.category = categoryDto;

    //     dto.createdAt = entity.getCreatedAt();
    //     dto.updatedAt = entity.getUpdatedAt();

    //     return dto;
    // }

}