package com.menufoods.services.user;

import com.menufoods.model.dto.user.CreateUpdateUserDTO;
import com.menufoods.model.dto.user.UserResponseDTO;

public interface iUserService {

    UserResponseDTO create(CreateUpdateUserDTO user);

    UserResponseDTO findByEmail(String email);

    UserResponseDTO updateUser(CreateUpdateUserDTO data);

    void toggleLevelUser(String email, int level);


}
