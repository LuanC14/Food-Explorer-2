package com.foodexplorer.model.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String isAdmin;
    private String photoProfileUrl;
}
