package ec.edu.ups.icc.fundamentos01.users.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

import ec.edu.ups.icc.fundamentos01.users.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController {


     private final UserService service;

    // Inyección de dependencias por constructor
    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Object findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping
    public UserResponseDto create(@RequestBody CreateUserDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable int id, @RequestBody UpdateUserDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(
            @PathVariable int id,
            @RequestBody PartialUpdateUserDto dto) {
        return service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        return service.delete(id);
    }

}




    //     private List<User> users = new ArrayList<>();
    // private int currentId = 1;

    // @GetMapping
    // public List<UserResponseDto> findAll() {

    //     // Programación tradicional iterativa para mapear cada User a UserResponseDto
    //     // List<UserResponseDto> dtos = new ArrayList<>();
    //     // for (User user : users) {
    //     // dtos.add(UserMapper.toResponse(user));
    //     // }
    //     // return dtos;

    //     // Programación funcional para mapear cada User a UserResponseDto
    //     return users.stream()
    //             .map(UserMapper::toResponse)
    //             .toList();
    // }

    // @GetMapping("/{id}")
    // public Object findOne(@PathVariable int id) {

    //     // Programación tradicional iterativa para mapear cada User a UserResponseDto
    //     // Busqueda Lineal
    //     for (User user : users) {
    //         if (user.getId() == id) {
    //             return UserMapper.toResponse(user);
    //         }
    //     }
    //     return new Object() {
    //         public String error = "User not found";
    //     };

    //     // Programación funcional para mapear cada User a UserResponseDto
    //     // Busqueda Lineal
    //     // return users.stream()
    //     // .filter(u -> u.getId() == id)
    //     // .findFirst()
    //     // .map(UserMapper::toResponse)
    //     // .orElseGet(() -> new Object() {
    //     // public String error = "User not found";
    //     // });
    // }

    // @PostMapping
    // public UserResponseDto create(@RequestBody CreateUserDto dto) {
    //     User user = UserMapper.toEntity(currentId++, dto.name, dto.email);
    //     users.add(user);
    //     return UserMapper.toResponse(user);
    // }

    // @PutMapping("/{id}")
    // public Object update(@PathVariable int id, @RequestBody UpdateUserDto dto) {

    //     // Programacion tradicional iterativa
    //     // for (User user : users) {
    //     // if (user.getId() == id) {
    //     // user.setName(dto.name);
    //     // user.setEmail(dto.email);
    //     // return UserMapper.toResponse(user);
    //     // }
    //     // }
    //     // return new Object() {
    //     // public String error = "User not found";
    //     // };

    //     // Programacion funcional
    //     User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    //     if (user == null)
    //         return new Object() {
    //             public String error = "User not found";
    //         };

    //     user.setName(dto.name);
    //     user.setEmail(dto.email);

    //     return UserMapper.toResponse(user);
    // }

    // @PatchMapping("/{id}")
    // public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateUserDto dto) {

    //     // Programacion tradicional iterativa
    //     // for (User user : users) {
    //     // // ESTE ES EL CAMBIO pero deberia estar en un metodo aparte para evitar
    //     // // duplicacion de codigo y mejorar mantenibilidad con separacion de
    //     // // responsabilidades.
    //     // if (user.getId() == id) {
    //     // if (dto.name != null)
    //     // user.setName(dto.name);
    //     // if (dto.email != null)
    //     // user.setEmail(dto.email);
    //     // return UserMapper.toResponse(user);
    //     // }
    //     // }
    //     // return new Object() {
    //     // public String error = "User not found";
    //     // };

    //     // Programacion funcional
    //     User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    //     if (user == null)
    //         return new Object() {
    //             public String error = "User not found";
    //         };

    //     if (dto.name != null)
    //         user.setName(dto.name);
    //     if (dto.email != null)
    //         user.setEmail(dto.email);

    //     return UserMapper.toResponse(user);
    // }

    // @DeleteMapping("/{id}")
    // public Object delete(@PathVariable int id) {
    //     // Programacion tradicional iterativa
    //     // Iterator<User> iterator = users.iterator();
    //     // while (iterator.hasNext()) {
    //     //     User user = iterator.next();
    //     //     if (user.getId() == id) {
    //     //         iterator.remove();
    //     //         return new Object() {
    //     //             public String message = "Deleted successfully";
    //     //         };
    //     //     }
    //     // }
    //     // return new Object() {
    //     //     public String error = "User not found";
    //     // };


    //     // Programacion funcional
    //     boolean exists = users.removeIf(u -> u.getId() == id);
    //     if (!exists)
    //         return new Object() {
    //             public String error = "User not found";
    //         };

    //     return new Object() {
    //         public String message = "Deleted successfully";
    //     };
    // }