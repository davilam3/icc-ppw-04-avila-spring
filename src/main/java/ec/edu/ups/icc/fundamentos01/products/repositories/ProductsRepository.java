package ec.edu.ups.icc.fundamentos01.products.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductsEntity;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    Optional<ProductsEntity> findByName( String name);

    //select * from products where user_id=?
    //owner tiene el userentity
    List<ProductsEntity> findByOwnerId( Long userId);

    // List<ProductsEntity> findByCategoryId( Long categoryId);
    List<ProductsEntity> findByCategoriesId( Long categoryId);
    
    List<ProductsEntity> findByOwnerName( String name);
    
    List<ProductsEntity> findByCategoriesName( String name);

    List<ProductsEntity> findByCategoryIdAndPriceGreaterThan( Long categoryId, Double price);


}