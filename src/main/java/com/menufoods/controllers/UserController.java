package com.menufoods.controllers;

import com.menufoods.domain.dto.user.CreateUpdateUserDTO;
import com.menufoods.domain.dto.user.UserResponseDTO;
import com.menufoods.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<UserResponseDTO> create(@RequestBody CreateUpdateUserDTO request) {
        UserResponseDTO user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    ResponseEntity<UserResponseDTO> getByEmail(@RequestParam("email") String request) {
        UserResponseDTO user = userService.findByEmail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    ResponseEntity<UserResponseDTO> update(@RequestBody CreateUpdateUserDTO request) {
        UserResponseDTO user = userService.updateUser(request);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping
    ResponseEntity uploadoPhotoProfile(@RequestParam("photo")MultipartFile photo) {
        userService.uploadPhotoProfile(photo);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/acess")
    ResponseEntity changeLevelUser(@RequestParam("email") String email, @RequestParam("level") int level) {
        System.out.println(email + level);
        userService.toggleLevelUser(email, level);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    ResponseEntity deleteUser() {
        userService.delete();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
