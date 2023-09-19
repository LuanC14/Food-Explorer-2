package com.foodexplorer.model.dto;

import com.foodexplorer.model.entities.UserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    Long id;
    String name;
    String email;
    UserRole role;
    String photoProfileUrl;
}
