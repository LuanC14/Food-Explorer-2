package com.menufoods.domain.dto.ingredient;

import java.util.List;

public record IngredientResponseDTO(String name, Long id, List<Long> menuItemsIdList) {
}
