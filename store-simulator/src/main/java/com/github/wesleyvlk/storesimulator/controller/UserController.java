package com.github.wesleyvlk.storesimulator.controller;

import com.github.wesleyvlk.storesimulator.domain.model.User;
import com.github.wesleyvlk.storesimulator.domain.model.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<User> getUserCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(userService.findByCpf(cpf));
    }
}
