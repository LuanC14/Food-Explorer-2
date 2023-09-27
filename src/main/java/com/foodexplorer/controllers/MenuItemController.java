package com.foodexplorer.controllers;

import com.foodexplorer.model.dto.item.CreateUpdateItemDTO;
import com.foodexplorer.model.dto.item.MenuItemResponseDTO;
import com.foodexplorer.services.menuItem.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/menuitem")
public class MenuItemController {

    @Autowired
    MenuItemService service;

    @PostMapping
    ResponseEntity<MenuItemResponseDTO> create(@RequestBody CreateUpdateItemDTO request) {
        MenuItemResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    ResponseEntity<MenuItemResponseDTO> update(@RequestParam("id") Long id, @RequestBody CreateUpdateItemDTO request) {
        MenuItemResponseDTO response = service.update(request, id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    ResponseEntity<List<MenuItemResponseDTO>> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(service.findByName(name));
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<MenuItemResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @DeleteMapping
    ResponseEntity delete(@RequestParam("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
