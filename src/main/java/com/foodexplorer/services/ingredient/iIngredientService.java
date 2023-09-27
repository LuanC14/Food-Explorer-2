package com.foodexplorer.services.ingredient;

import com.foodexplorer.model.dto.ingredient.CreateUpdateIngredientDTO;
import com.foodexplorer.model.dto.ingredient.IngredientResponseDTO;

import java.util.List;

public interface iIngredientService {

    IngredientResponseDTO create(CreateUpdateIngredientDTO data);

    IngredientResponseDTO updateIngredientAddNewMenuItemInList(CreateUpdateIngredientDTO data);

    IngredientResponseDTO findByName(String name);

    List<IngredientResponseDTO> findAll();

    void delete(String name, Long itemId);
}
