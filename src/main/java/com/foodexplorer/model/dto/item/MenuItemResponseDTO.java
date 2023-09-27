package com.foodexplorer.model.dto.item;

import com.foodexplorer.model.entities.MenuItem.MenuItemType;

import java.util.List;

public record MenuItemResponseDTO(Long id, Long userId, String name, String description,
                                  MenuItemType type, List<String> ingredients, String photoUrl) {
}
