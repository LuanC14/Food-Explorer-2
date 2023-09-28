package com.menufoods.model.dto.item;

import com.menufoods.model.entities.MenuItem.MenuItemType;

import java.util.List;

public record MenuItemResponseDTO(Long id, Long userId, String name, String description,
                                  MenuItemType type, List<String> ingredients, String photoUrl) {
}
