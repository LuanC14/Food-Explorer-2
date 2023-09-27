package com.foodexplorer.model.entities.MenuItem;

import com.foodexplorer.model.entities.Ingredient.Ingredient;
import com.foodexplorer.model.entities.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity(name = "itens_cardapio")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @NotNull
    @Column(name = "tipo")
    private MenuItemType type;

    @Column
    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "menuItems")
    private List<Ingredient> ingredients = new ArrayList<>();

}
