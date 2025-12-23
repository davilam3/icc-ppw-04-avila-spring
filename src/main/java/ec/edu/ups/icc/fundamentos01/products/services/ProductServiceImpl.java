package ec.edu.ups.icc.fundamentos01.products.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductsDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Products;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductsMapper;


@Service
public class ProductServiceImpl implements ProductService{

    private int currentId = 1;
    private List<Products> products = new ArrayList<>(List.of(
        new Products(currentId++, "Laptop", "High performance laptop", 1200.00),
        new Products(currentId++, "Smartphone", "Latest model smartphone", 800.00),
        new Products(currentId++, "Headphones"  , "Noise-cancelling headphones", 150.00),
        new Products(currentId++, "Keyboard", "Mechanical keyboard", 120.00),
        new Products(currentId++, "Monitor", "4K monitor", 350.00)
    ));

    @Override
    public List<ProductsResponseDto> findAll() {
        return products.stream().map(ProductsMapper::toResponse).toList();
    }

    @Override
    public Object findOne(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(ProductsMapper::toResponse)
                .orElseGet(() -> null);
    }

    @Override
    public ProductsResponseDto create(CreateProductsDto dto) {
        Products product = ProductsMapper.toEntity(currentId++, dto.name, dto.description, dto.price);
        products.add(product);
        return ProductsMapper.toResponse(product);
    }

    @Override
    public Object update(int id, UpdateProductsDto dto) {
        Products product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null)
            return new Object() {
                public String error = "Product not found";
            };
        product.setName(dto.name);
        product.setDescription(dto.description);
        product.setPrice(dto.price);

        return ProductsMapper.toResponse(product);
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateProductsDto dto) {
        Products product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product == null)
            return new Object() {
                public String error = "Product not found";
            };
        if (dto.name != null)
            product.setName(dto.name);
        if (dto.description != null)
            product.setDescription(dto.description);
        if (dto.price != null)
            product.setPrice(dto.price);

        return ProductsMapper.toResponse(product);
    }

    @Override
    public Object delete(int id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (!removed)
            return new Object() {
                public String error = "Product not found";
            };
        return new Object() {
            public String message = "Deleted successfully";
        };
    }
}
