package com.menufoods.repositories;

import com.menufoods.domain.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByNameContainingIgnoreCase(String name);

}
