package com.foodexplorer.model.dto;

public record CreateOrUpdateUserDTO(String name, String email, String password, String newPassword) {

}
