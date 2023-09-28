package com.menufoods.model.entities.Ingredient;

import com.menufoods.model.entities.MenuItem.MenuItem;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "ingredientes")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "relacao_item_ingrediente",
            joinColumns = @JoinColumn(name = "ingrediente_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id", referencedColumnName = "id"))
    private List<MenuItem> menuItems = new ArrayList<>();

    @Column(unique = true, name = "ingrediente")
    private String name;

    public void setMenuItem(MenuItem item) {
        this.menuItems.add(item);
    }

    public List<Long> getMenuItemsId() {
       return this.menuItems
                .stream()
                .map(MenuItem::getId)
                .toList();
    }

    public Long getMenuItemIdByDataId(Long id) {
        return menuItems.stream()
                .filter(menuItem -> menuItem.getId().equals(id))
                .map(MenuItem::getId)
                .findFirst()
                .get();
        }
    }
