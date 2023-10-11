package com.menufoods.services.ingredient;

import com.menufoods.domain.dto.ingredient.CreateUpdateIngredientDTO;
import com.menufoods.domain.dto.ingredient.IngredientResponseDTO;

import java.util.List;

public interface IIngredientService {

    IngredientResponseDTO create(CreateUpdateIngredientDTO data);

    IngredientResponseDTO updateIngredientAddNewMenuItemInList(CreateUpdateIngredientDTO data);

    IngredientResponseDTO findByName(String name);

    List<IngredientResponseDTO> findAll();

    void delete(String name, Long itemId);
}
