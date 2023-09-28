package com.menufoods.services.menuItem;

import com.menufoods.model.dto.item.CreateUpdateItemDTO;
import com.menufoods.model.dto.item.MenuItemResponseDTO;
import com.menufoods.model.entities.MenuItem.MenuItem;

import java.util.List;

public interface iMenuItemService {

    MenuItemResponseDTO create(CreateUpdateItemDTO data);

    MenuItemResponseDTO update(CreateUpdateItemDTO data, Long id);

    List<MenuItemResponseDTO> findByName(String name);

    List<MenuItemResponseDTO> findAll();

    MenuItem getById(Long itemId);

    void delete(Long id);

}
