package com.foodexplorer.services.user;

import com.foodexplorer.model.dto.CreateOrUpdateUserDTO;
import com.foodexplorer.model.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface iUserService {

     UserResponseDTO create(CreateOrUpdateUserDTO user);

     UserResponseDTO getByEmail(String email);

     UserResponseDTO updateUser(CreateOrUpdateUserDTO data);


}
