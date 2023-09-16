package com.foodexplorer.controllers;

import com.foodexplorer.model.dto.UserDto;
import com.foodexplorer.model.dto.UserResponseDto;
import com.foodexplorer.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    ResponseEntity<UserResponseDto> create(@RequestBody UserDto request) {
        UserResponseDto user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    ResponseEntity<UserResponseDto> getByEmail(@RequestParam("email") String request) {
        UserResponseDto user = userService.getByEmail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    ResponseEntity<UserResponseDto> getByEmail(@RequestBody UserDto request ) {
        UserResponseDto user = userService.updateUser(request);
        return ResponseEntity.ok().body(user);
    }

}
