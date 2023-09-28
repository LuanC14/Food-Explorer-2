package com.menufoods.services.user;

import com.menufoods.exceptions.custom.DataConflictException;
import com.menufoods.exceptions.custom.DataNotFoundException;
import com.menufoods.exceptions.custom.UnathourizedException;
import com.menufoods.model.dto.user.CreateUpdateUserDTO;
import com.menufoods.model.dto.user.UserResponseDTO;
import com.menufoods.model.entities.User.User;
import com.menufoods.model.entities.User.UserRole;
import com.menufoods.providers.mapper.DozzerMapper;
import com.menufoods.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements iUserService {
    @Autowired
    UserRepository repository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserResponseDTO create(CreateUpdateUserDTO data) {

        Optional<User> optionalUser = repository.findByEmail(data.email());

        if (optionalUser.isPresent()) {
            throw new DataConflictException("Este email já está em uso!");
        }

        var encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(data.password());

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setLogin(data.email());
        newUser.setPassword(hashedPassword);
        repository.save(newUser);

        var getUserForSetRole = repository.findByEmail(data.email()).get();

        if (getUserForSetRole.getId() == 1) {
            getUserForSetRole.setRole(UserRole.MASTER);
        } else if (getUserForSetRole.getId() == 2) {
            getUserForSetRole.setRole(UserRole.ADMIN);
        } else {
            getUserForSetRole.setRole(UserRole.USER);
        }
        repository.save(getUserForSetRole);

        return DozzerMapper.parseObject(getUserForSetRole, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(CreateUpdateUserDTO data) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var emailAuthenticated = authentication.getName();
        User user = repository.findByEmail(emailAuthenticated).get();

        if (data.email() != null) {
            Optional<User> checkedEmailInUse = repository.findByEmail(data.email());

            if (checkedEmailInUse.isPresent() && checkedEmailInUse.get().getEmail() != user.getEmail()) {
                throw new DataConflictException("Esse email já está em uso");
            }
            user.setEmail(data.email());
            user.setLogin(data.email());
        }

        if (data.password() != null) {
            var encoder = new BCryptPasswordEncoder();
            boolean checkPasswordMatch = encoder.matches(data.password(), user.getPassword());

            if (!checkPasswordMatch) {
                throw new UnathourizedException("Senha incorreta.");
            }
            user.setPassword(encoder.encode(data.password()));
        }

        user.setName(data.name() != null ? data.name() : user.getName());
        repository.save(user);

        return DozzerMapper.parseObject(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO findByEmail(String email) {

        Optional<User> optionalUser = repository.findByEmail(email);

        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException("Esse email não consta em nosso banco de dados.");
        }

        return DozzerMapper.parseObject(optionalUser.get(), UserResponseDTO.class);
    }

    public void delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        repository.delete(user);
    }

    @Override
    public void toggleLevelUser(String email, int level) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var emailAuthenticated = authentication.getName();
        User authenticatedUser = repository.findByEmail(emailAuthenticated).get();

        Optional<User> optionalUserTarget = repository.findByEmail(email);

        if (!optionalUserTarget.isPresent()) {
            throw new DataNotFoundException("Usuário não encontrado");
        } else if (optionalUserTarget.get().getId() == 1) {
            throw new UnathourizedException("O primeiro usuário mestre não pode ter suas permissões alteradas!");
        } else if (optionalUserTarget.get().getRole() == UserRole.MASTER && authenticatedUser.getRole() != UserRole.MASTER) {
            throw new UnathourizedException("Apenas usuários mestres podem alterar permissões de outros mestres.");
        } else if (authenticatedUser.getRole() == UserRole.ADMIN && level == 0) {
            throw new UnathourizedException("Você não pode inserir um novo mestre");
        } else if (authenticatedUser.getRole() == UserRole.MASTER
                && authenticatedUser.getId() != 1
                && optionalUserTarget.get().getRole() == UserRole.MASTER
                && level != 0
        ) {
            throw new UnathourizedException("Apenas o mestre primário pode remover outro mestre");
        }

        User userTarget = optionalUserTarget.get();
        if (level == 0) userTarget.setRole(UserRole.MASTER);
        if (level == 1) userTarget.setRole(UserRole.ADMIN);
        if (level == 2) userTarget.setRole(UserRole.USER);

        repository.save(userTarget);
    }
}
