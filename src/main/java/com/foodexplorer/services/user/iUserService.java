package com.foodexplorer.services.user;

import com.foodexplorer.model.dto.UserDto;
import com.foodexplorer.model.dto.UserResponseDto;

public interface iUserService {

     UserResponseDto create(UserDto user);

     UserResponseDto getByEmail(String email);

     UserResponseDto updateUser(UserDto data, String email);


}
