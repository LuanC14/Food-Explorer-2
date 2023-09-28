package com.menufoods.model.dto.user;

import com.menufoods.model.entities.User.UserRole;
import lombok.Data;

// Não está em formato Record pois está sendo utilizado pelo DozzerMapper, e ele não reconhece classes desse tipo
@Data
public class UserResponseDTO {
    Long id;
    String name;
    String email;
    UserRole role;
    String photoProfileUrl;
}
