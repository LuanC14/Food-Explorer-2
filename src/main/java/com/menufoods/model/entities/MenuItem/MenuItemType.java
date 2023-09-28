package com.menufoods.model.entities.MenuItem;

public enum MenuItemType {
    SNACK("snack"),
    DESSERT("dessert"),
    DRINK("DRINK");

    private String type;
    MenuItemType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

