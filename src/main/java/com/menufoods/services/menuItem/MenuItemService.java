package com.menufoods.services.menuItem;

import com.menufoods.exceptions.custom.DataConflictException;
import com.menufoods.exceptions.custom.DataNotFoundException;
import com.menufoods.domain.dto.item.CreateUpdateItemDTO;
import com.menufoods.domain.dto.item.MenuItemResponseDTO;
import com.menufoods.domain.model.Ingredient;
import com.menufoods.domain.model.MenuItem;
import com.menufoods.domain.enums.MenuItemType;
import com.menufoods.domain.model.User;
import com.menufoods.repositories.MenuItemRepository;
import com.menufoods.services.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService implements iMenuItemService {

    @Autowired
    MenuItemRepository repository;

    @Autowired
    UploadService uploadService;

    @Override
    public MenuItemResponseDTO create(CreateUpdateItemDTO data) {

        var verifyInUse = repository.findByNameContainingIgnoreCase(data.name());

        if (!verifyInUse.isEmpty()) {
            throw new DataConflictException("Esse nome já está em uso");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        MenuItem newItem = new MenuItem();
        newItem.setUser(user);
        newItem.setType(MenuItemType.valueOf(data.type().toUpperCase()));
        newItem.setName(data.name().toLowerCase());
        newItem.setDescription(data.description());
        repository.save(newItem);

        return new MenuItemResponseDTO(newItem.getId(), newItem.getUser().getId(), newItem.getName(),
                newItem.getDescription(), newItem.getType(), null, newItem.getPhotoUrl());
    }

    @Override
    public MenuItemResponseDTO update(CreateUpdateItemDTO data, Long id) {

        MenuItem menuItem = repository.findById(id).get();
        menuItem.setName(data.name() != null ? data.name().toLowerCase() : menuItem.getName());
        menuItem.setType(data.type() != null ? MenuItemType.valueOf(data.type()) : menuItem.getType());
        menuItem.setDescription(data.description() != null ? data.description() : menuItem.getDescription());

        repository.save(menuItem);

        List<String> ingredientsNameList = menuItem.getIngredients().stream().map(Ingredient::getName).toList();

        return new MenuItemResponseDTO(
                menuItem.getId(), menuItem.getUser().getId(), menuItem.getName(),
                menuItem.getDescription(), menuItem.getType(), ingredientsNameList, menuItem.getPhotoUrl());
    }

    @Override
    public void uploadPhotoItem(MultipartFile photo, Long itemId) {
        MenuItem item = repository.findById(itemId).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (item.getPhotoUrl() != null) {
            uploadService.deleteFile(item.getPhotoUrl());
        }

        String filename = "item" + "_" + item.getName() + item.getId() + "." + photo.getOriginalFilename()
                .substring(photo.getOriginalFilename().lastIndexOf(".") + 1);


        String photoUri = uploadService.uploadFile(photo, filename);
        // Replacement as white spaces break the link
        String photoUriWithoutBlankSpaces = photoUri.replace(" ", "+");

        item.setPhotoUrl(photoUriWithoutBlankSpaces);
        repository.save(item);
    }

    @Override
    public List<MenuItemResponseDTO> findByName(String name) {
        List<MenuItem> optionalMenuItem = repository.findByNameContainingIgnoreCase(name.toLowerCase());

        if (!optionalMenuItem.isEmpty()) {
            List<MenuItem> menuItems = optionalMenuItem.stream().toList();
            List<MenuItemResponseDTO> menuItemsResponseList = new ArrayList<>();

            for (MenuItem item : menuItems) {
                List<String> ingredientsNameList = item.getIngredients().stream().map(Ingredient::getName).toList();

                MenuItemResponseDTO itemResponse = new MenuItemResponseDTO(item.getId(), item.getUser().getId(),
                        item.getName(), item.getDescription(), item.getType(), ingredientsNameList, item.getPhotoUrl());

                menuItemsResponseList.add(itemResponse);
            }

            return menuItemsResponseList;

        } else {
            throw new DataNotFoundException("Nenhum item encontrado");
        }
    }

    @Override
    public List<MenuItemResponseDTO> findAll() {
        var optionalListMenuItems = repository.findAll();
        List<MenuItemResponseDTO> responseListMenuItems = new ArrayList<>();

        if (!optionalListMenuItems.isEmpty()) {
            List<MenuItem> allMenuItems = optionalListMenuItems.stream().toList();

            List<Ingredient> listAllIngredients = allMenuItems.stream()
                    .flatMap(menuItem -> menuItem.getIngredients().stream())
                    .toList();

            for (MenuItem item : allMenuItems) {

                List<String> ingredientsName = item.getIngredients().stream().map(i -> i.getName()).toList();

                MenuItemResponseDTO responseItem = new MenuItemResponseDTO(
                        item.getId(), item.getUser().getId(), item.getName(),
                        item.getDescription(), item.getType(), ingredientsName, item.getPhotoUrl());

                responseListMenuItems.add(responseItem);
            }
        } else {
            throw new DataNotFoundException("Não há nenhum item criado");
        }

        return responseListMenuItems;
    }

    @Override
    public void delete(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<MenuItem> optionalMenuItem = repository.findById(id);

        if (optionalMenuItem.isPresent()) {
            MenuItem menuItem = optionalMenuItem.get();
            if (menuItem.getPhotoUrl() != null) {
                uploadService.deleteFile(menuItem.getPhotoUrl());
            }
            repository.delete(menuItem);
        } else {
            throw new DataNotFoundException("Item não encontrado");
        }
    }

    @Override
    public MenuItem getMenuItemEntity(Long itemId) {
        return repository.findById(itemId).get();
    }
}
