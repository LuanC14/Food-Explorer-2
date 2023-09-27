package com.foodexplorer.controllers;

import com.foodexplorer.model.dto.ingredient.CreateUpdateIngredientDTO;
import com.foodexplorer.model.dto.ingredient.IngredientResponseDTO;
import com.foodexplorer.services.ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ingredient")
public class IngredientController {

    @Autowired
    IngredientService service;

    @PostMapping
    ResponseEntity<IngredientResponseDTO> create(@RequestBody CreateUpdateIngredientDTO request) {
        var response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    ResponseEntity<IngredientResponseDTO> findByName(@RequestParam("name") String name) {
        IngredientResponseDTO response = service.findByName(name);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    ResponseEntity<List<IngredientResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @DeleteMapping
    ResponseEntity delete(@RequestParam("name") String name, @RequestParam("itemId") Long itemId) {
        service.delete(name, itemId);
        return ResponseEntity.ok().build();
    }
}
