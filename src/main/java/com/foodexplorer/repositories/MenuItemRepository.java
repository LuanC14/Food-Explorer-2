package com.foodexplorer.repositories;

import com.foodexplorer.model.entities.MenuItem.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByNameContainingIgnoreCase(String name);

}
