package com.menufoods.services.menuItem;

import com.menufoods.exceptions.custom.DataNotFoundException;
import com.menufoods.model.dto.item.CreateUpdateItemDTO;
import com.menufoods.model.dto.item.MenuItemResponseDTO;
import com.menufoods.model.entities.Ingredient.Ingredient;
import com.menufoods.model.entities.MenuItem.MenuItem;
import com.menufoods.model.entities.MenuItem.MenuItemType;
import com.menufoods.model.entities.User.User;
import com.menufoods.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService implements iMenuItemService {

    @Autowired
    MenuItemRepository repository;

    @Override
    public MenuItemResponseDTO create(CreateUpdateItemDTO data) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        MenuItem newItem = new MenuItem();
        newItem.setUser(user);
        newItem.setType(MenuItemType.valueOf(data.type().toUpperCase()));
        newItem.setName(data.name().toLowerCase());
        newItem.setDescription(data.description());
        repository.save(newItem);

        return new MenuItemResponseDTO(newItem.getId(), newItem.getUser().getId(), newItem.getName(),
                newItem.getDescription(), newItem.getType(), null, newItem.getPhotoUrl());
    }

    @Override
    public MenuItemResponseDTO update(CreateUpdateItemDTO data, Long id) {

        MenuItem menuItem = repository.findById(id).get();
        menuItem.setName(data.name() != null ? data.name().toLowerCase() : menuItem.getName());
        menuItem.setType(data.type() != null ? MenuItemType.valueOf(data.type()) : menuItem.getType());
        menuItem.setDescription(data.description() != null ? data.description() : menuItem.getDescription());

        repository.save(menuItem);

        List<String> ingredientsNameList = menuItem.getIngredients().stream().map(Ingredient::getName).toList();

        return new MenuItemResponseDTO(
                menuItem.getId(), menuItem.getUser().getId(), menuItem.getName(),
                menuItem.getDescription(), menuItem.getType(), ingredientsNameList, menuItem.getPhotoUrl());
    }

    @Override
    public List<MenuItemResponseDTO> findByName(String name) {
        List<MenuItem> optionalMenuItem = repository.findByNameContainingIgnoreCase(name.toLowerCase());

        if (!optionalMenuItem.isEmpty()) {
            List<MenuItem> menuItems = optionalMenuItem.stream().toList();
            List<MenuItemResponseDTO> menuItemsResponseList = new ArrayList<>();

            for(MenuItem item : menuItems) {
                List<String> ingredientsNameList = item.getIngredients().stream().map(Ingredient::getName).toList();

                MenuItemResponseDTO itemResponse = new MenuItemResponseDTO(item.getId(), item.getUser().getId(),
                        item.getName(), item.getDescription(), item.getType(), ingredientsNameList, item.getPhotoUrl());

                menuItemsResponseList.add(itemResponse);
            }

            return menuItemsResponseList;

        } else {
            throw new DataNotFoundException("Nenhum item encontrado");
        }
    }

    @Override
    public List<MenuItemResponseDTO> findAll() {
        var optionalListMenuItems = repository.findAll();
        List<MenuItemResponseDTO> responseListMenuItems = new ArrayList<>();

        if (!optionalListMenuItems.isEmpty()) {
            List<MenuItem> allMenuItems = optionalListMenuItems.stream().toList();

            List<Ingredient> listAllIngredients = allMenuItems.stream()
                    .flatMap(menuItem -> menuItem.getIngredients().stream())
                    .toList();

            for (MenuItem item : allMenuItems) {

                List<String> ingredientsName = item.getIngredients().stream().map(i -> i.getName()).toList();

                MenuItemResponseDTO responseItem = new MenuItemResponseDTO(
                        item.getId(), item.getUser().getId(), item.getName(),
                        item.getDescription(), item.getType(), ingredientsName, item.getPhotoUrl());

                responseListMenuItems.add(responseItem);
            }
        } else {
            throw new DataNotFoundException("Não há nenhum item criado");
        }

        return responseListMenuItems;
    }

    @Override
    public void delete(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<MenuItem> menuItem = repository.findById(id);

        if (menuItem.isPresent()) {
            repository.delete(menuItem.get());
        } else {
            throw new DataNotFoundException("Item não encontrado");
        }
    }

    @Override
    public MenuItem getById(Long itemId) {
        return repository.findById(itemId).get();
    }
}
