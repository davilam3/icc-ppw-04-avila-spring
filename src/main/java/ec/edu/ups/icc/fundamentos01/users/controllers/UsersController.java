package ec.edu.ups.icc.fundamentos01.users.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto findOne(@PathVariable("id") Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDto>> getProductsByUserId(
            @PathVariable("id") Long userId) { // saca los datos de la URL

        List<ProductResponseDto> products = userService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    // public list,ProductResponseDto, getProductsByUserId(userId){
    // }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(
            @Valid @RequestBody CreateUserDto userDto) {
        UserResponseDto created = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable("id") Long id, @RequestBody UpdateUserDto dto) {
        return userService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody PartialUpdateUserDto dto) {
        return userService.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products-v2")
    public List<ProductResponseDto> getProductsByUserV2(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId) {
        return userService.getProductsByUserIdWithFilters(
                id,
                name,
                minPrice,
                maxPrice,
                categoryId);
    }

}