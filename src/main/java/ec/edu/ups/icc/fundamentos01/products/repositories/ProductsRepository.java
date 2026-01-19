package ec.edu.ups.icc.fundamentos01.products.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductsEntity;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

        Optional<ProductsEntity> findByName(String name);

        /**
         * Encuentra todos los productos de un usuario específico
         * Spring Data JPA genera: SELECT * FROM products WHERE user_id = ?
         */
        List<ProductsEntity> findByOwnerId(Long userId);

        /**
         * Encuentra todos los productos de una categoría específica
         * Spring Data JPA genera: SELECT * FROM products WHERE category_id = ?
         */
        // List<ProductsEntity> findByCategoriesId(Long categoryId);

        /**
         * Encuentra productos por nombre de usuario
         * Genera JOIN automáticamente:
         * SELECT p.* FROM products p JOIN users u ON p.user_id = u.id WHERE u.name = ?
         */
        List<ProductsEntity> findByOwnerName(String ownerName);

        /**
         * Encuentra productos por nombre de categoría
         * Genera JOIN automáticamente:
         * SELECT p.* FROM products p JOIN categories c ON p.category_id = c.id WHERE
         * c.name = ?
         */
        // List<ProductsEntity> findByCategoriesName(String categoryName);

        /**
         * Encuentra productos con precio mayor a X de una categoría específica
         * Consulta con múltiples condiciones
         * Genera:
         * SELECT p.* FROM products p WHERE p.category_id = ? AND p.price > ?
         */
        // List<ProductsEntity> findByCategoryIdAndPriceGreaterThan(Long categoryId,
        // Double price);

        List<ProductsEntity> findByCategoriesId(Long categoryId);

        List<ProductsEntity> findByCategoriesName(String name);

        @Query("SELECT p FROM ProductsEntity p " +
                        "WHERE SIZE(p.categories) >= :categoryCount " +
                        "AND :categoryCount = " +
                        "(SELECT COUNT(c) FROM p.categories c WHERE c.id IN :categoryIds)")
        List<ProductsEntity> findByAllCategories(@Param("categoryIds") List<Long> categoryIds,
                        @Param("categoryCount") long categoryCount);

        List<ProductsEntity> findByCategoriesIdAndPriceGreaterThan(Long categoryId, Double price);

}