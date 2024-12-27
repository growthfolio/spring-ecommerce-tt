package com.distribuidorasafari.ecommerce.controller;


import com.distribuidorasafari.ecommerce.model.User;
import com.distribuidorasafari.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import com.distribuidorasafari.ecommerce.service.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/names/{name}")
    public ResponseEntity<List<User>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(userRepository.findAllByNameContainingIgnoreCase(name));
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserLogin> authenticateUser(@RequestBody Optional<UserLogin> userLogin) {
//        return userService.authenticateUser(userLogin)
//                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
//                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
//    }

//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
//        return userService.registerUser(user)
//                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
//                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
//    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
//        return userService.updateUser(user)
//                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
}