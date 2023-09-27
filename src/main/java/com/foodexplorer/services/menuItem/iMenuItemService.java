package com.foodexplorer.services.menuItem;

import com.foodexplorer.model.dto.item.CreateUpdateItemDTO;
import com.foodexplorer.model.dto.item.MenuItemResponseDTO;
import com.foodexplorer.model.entities.MenuItem.MenuItem;

import java.util.List;

public interface iMenuItemService {

    MenuItemResponseDTO create(CreateUpdateItemDTO data);

    MenuItemResponseDTO update(CreateUpdateItemDTO data, Long id);

    List<MenuItemResponseDTO> findByName(String name);

    List<MenuItemResponseDTO> findAll();

    MenuItem getById(Long itemId);

    void delete(Long id);

}
