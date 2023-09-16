package com.foodexplorer.services.User;

import com.foodexplorer.model.dto.UserDto;
import com.foodexplorer.model.dto.UserResponseDto;

public interface iUserService {

     UserResponseDto create(UserDto user);

     UserResponseDto getByEmail(String email);


}
