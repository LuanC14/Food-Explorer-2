package com.menufoods.services.ingredient;

import com.menufoods.model.dto.ingredient.CreateUpdateIngredientDTO;
import com.menufoods.model.dto.ingredient.IngredientResponseDTO;

import java.util.List;

public interface iIngredientService {

    IngredientResponseDTO create(CreateUpdateIngredientDTO data);

    IngredientResponseDTO updateIngredientAddNewMenuItemInList(CreateUpdateIngredientDTO data);

    IngredientResponseDTO findByName(String name);

    List<IngredientResponseDTO> findAll();

    void delete(String name, Long itemId);
}
