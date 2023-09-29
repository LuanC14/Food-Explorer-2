package com.menufoods.domain.dto.item;

import com.menufoods.domain.enums.MenuItemType;

import java.util.List;

public record MenuItemResponseDTO(Long id, Long userId, String name, String description,
                                  MenuItemType type, List<String> ingredients, String photoUrl) {
}
