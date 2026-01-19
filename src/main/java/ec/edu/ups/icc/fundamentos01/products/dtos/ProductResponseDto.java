package ec.edu.ups.icc.fundamentos01.products.dtos;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoriaResponseDto;

public class ProductResponseDto {

    public Long id;
    public String name;
    public Double price;
    public String description;
    public String reason;

    // ============== OBJETOS ANIDADOS ==============

    public UserSummaryDto user;
    public List<CategoriaResponseDto> categories;

    // ============== AUDITORÍA ==============

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    // ============== DTOs INTERNOS ==============

    public static class UserSummaryDto {
        public Long id;
        public String name;
        public String email;
    }

    // ===== CONSTRUCTOR VACÍO (NECESARIO PARA MAPPERS) =====
    public ProductResponseDto() {
    }

    // ===== CONSTRUCTOR PARA /users/{id}/products =====
    public ProductResponseDto(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description= description;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserSummaryDto getUser() {
        return user;
    }

    public void setUser(UserSummaryDto user) {
        this.user = user;
    }

    public List<CategoriaResponseDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriaResponseDto> categories) {
        this.categories = categories;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    

}