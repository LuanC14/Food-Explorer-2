package com.menufoods.services.menuItem;

import com.menufoods.domain.dto.item.CreateUpdateItemDTO;
import com.menufoods.domain.dto.item.MenuItemResponseDTO;
import com.menufoods.domain.model.MenuItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMenuItemService {

    MenuItemResponseDTO create(CreateUpdateItemDTO data);

    MenuItemResponseDTO update(CreateUpdateItemDTO data, Long id);

    void uploadPhotoItem(MultipartFile photo, Long itemId);

    List<MenuItemResponseDTO> findByName(String name);

    List<MenuItemResponseDTO> findAll();

    MenuItem getMenuItemEntity(Long itemId);

    void delete(Long id);

}
