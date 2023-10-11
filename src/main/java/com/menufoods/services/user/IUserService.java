package com.menufoods.services.user;

import com.menufoods.domain.dto.user.CreateUpdateUserDTO;
import com.menufoods.domain.dto.user.UserResponseDTO;

public interface IUserService {

    UserResponseDTO create(CreateUpdateUserDTO user);

    UserResponseDTO findByEmail(String email);

    UserResponseDTO updateUser(CreateUpdateUserDTO data);

    void toggleLevelUser(String email, int level);


}
