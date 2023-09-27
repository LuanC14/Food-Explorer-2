package com.foodexplorer.services.user;

import com.foodexplorer.model.dto.user.CreateUpdateUserDTO;
import com.foodexplorer.model.dto.user.UserResponseDTO;

public interface iUserService {

    UserResponseDTO create(CreateUpdateUserDTO user);

    UserResponseDTO findByEmail(String email);

    UserResponseDTO updateUser(CreateUpdateUserDTO data);

    void toggleLevelUser(String email, int level);


}
