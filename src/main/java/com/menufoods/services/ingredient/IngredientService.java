package com.menufoods.services.ingredient;

import com.menufoods.exceptions.custom.DataNotFoundException;
import com.menufoods.domain.dto.ingredient.CreateUpdateIngredientDTO;
import com.menufoods.domain.dto.ingredient.IngredientResponseDTO;
import com.menufoods.domain.model.Ingredient;
import com.menufoods.domain.model.MenuItem;
import com.menufoods.repositories.IngredientRepository;
import com.menufoods.services.menuItem.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService implements iIngredientService {
    @Autowired
    private IngredientRepository repository;

    @Autowired
    MenuItemService menuItemService;

    @Override
    public IngredientResponseDTO create(CreateUpdateIngredientDTO data) {

        String lowerName = data.name().toLowerCase();

        MenuItem menuItem = menuItemService.getMenuItemEntity(data.itemId());
        Optional<Ingredient> optionalIngredient = repository.findByName(lowerName);

        if (optionalIngredient.isPresent()) {
            return this.updateIngredientAddNewMenuItemInList(data);
        }

        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(lowerName);
        newIngredient.setMenuItem(menuItem);
        var ingredientCreated = repository.save(newIngredient);

        return new IngredientResponseDTO(ingredientCreated.getName(), ingredientCreated.getId(), ingredientCreated.getMenuItemsId());
    }

    @Override
    public IngredientResponseDTO updateIngredientAddNewMenuItemInList(CreateUpdateIngredientDTO data) {

        String lowerName = data.name().toLowerCase();
        MenuItem menuItem = menuItemService.getMenuItemEntity(data.itemId());

        Ingredient ingredient = repository.findByName(lowerName).get();
        ingredient.getMenuItems().add(menuItem);
        var res = repository.save(ingredient);

        return new IngredientResponseDTO(res.getName(), res.getId(), res.getMenuItemsId());
    }

    @Override
    public IngredientResponseDTO findByName(String name) {
        Optional<Ingredient> optionalIngredient = repository.findByName(name.toLowerCase());

        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();

            return new IngredientResponseDTO(ingredient.getName(), ingredient.getId(), null);
        } else {
            throw new DataNotFoundException("Ingrediente não encontrado");
        }
    }

    @Override
    public List<IngredientResponseDTO> findAll() {
        List<Ingredient> ingredientList = repository.findAll();
        List<IngredientResponseDTO> ingredientListResponse = new ArrayList<>();

        if (!ingredientList.isEmpty()) {
            for (Ingredient ingredient : ingredientList) {
                IngredientResponseDTO ingredientResponse = new IngredientResponseDTO(
                        ingredient.getName(), ingredient.getId(), ingredient.getMenuItemsId());

                ingredientListResponse.add(ingredientResponse);
            }
            return ingredientListResponse;
        } else {
            throw new DataNotFoundException("Nenhum ingrediente encontrado");
        }
    }

    @Override
    public void delete(String name, Long itemId) {
        Optional<Ingredient> optionalIngredient = repository.findByName(name.toLowerCase());

        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();

            List<MenuItem> menuItemsListUsingThisIngredient = ingredient.getMenuItems();

            List<MenuItem> filteredList = menuItemsListUsingThisIngredient
                    .stream()
                    .filter(item -> item.getId() != itemId)
                    .toList();


            ingredient.setMenuItems(filteredList);

            if(ingredient.getMenuItems().isEmpty()) {
                repository.delete(ingredient);
            } else {
                repository.save(ingredient);
            }

        } else {
            throw new DataNotFoundException("Ingrediente não encontrado");
        }
    }
}
