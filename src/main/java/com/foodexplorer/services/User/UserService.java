package com.foodexplorer.services.User;

import com.foodexplorer.exceptions.custom.DataConflictException;
import com.foodexplorer.exceptions.custom.DataNotFoundException;
import com.foodexplorer.exceptions.GlobalExceptionHandler;
import com.foodexplorer.exceptions.custom.UnathourizedException;
import com.foodexplorer.model.dto.UserDto;
import com.foodexplorer.model.dto.UserResponseDto;
import com.foodexplorer.model.entities.User;
import com.foodexplorer.providers.mapper.DozzerMapper;
import com.foodexplorer.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements  iUserService {
    @Autowired
    UserRepository repository;

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public UserResponseDto create(UserDto data) {
            Optional<User> optionalUser = repository.findByEmail(data.getEmail());

            if(optionalUser.isPresent()) {
                throw new DataConflictException("Este email já está em uso!");
            }

            var encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(data.getPassword());

            User newUser = new User();
            newUser.setName(data.getName());
            newUser.setEmail(data.getEmail());
            newUser.setPassword(hashedPassword);

            repository.save(newUser);
            return DozzerMapper.parseObject(newUser, UserResponseDto.class);
    }

    public UserResponseDto getByEmail(String email) {

        Optional<User> optionalUser = repository.findByEmail(email);

        if(!optionalUser.isPresent()) {
            throw  new DataNotFoundException("Esse email não consta em nosso banco de dados.");
        }

        return DozzerMapper.parseObject(optionalUser.get(), UserResponseDto.class);
    }

    public UserResponseDto updateUser(UserDto data) {
        Optional<User> optionalUser = repository.findByEmail(data.getEmail());

        if(!optionalUser.isPresent()) {
            throw  new DataNotFoundException("Esse email não consta em nosso banco de dados.");
        }
        User user = optionalUser.get();

        var encoder = new BCryptPasswordEncoder();
        boolean checkPasswordMatch = encoder.matches(data.getPassword(), user.getPassword());

        if(!checkPasswordMatch) {
            throw new UnathourizedException("Senha incorreta");
        }

        user.setName(data.getName() != null ? data.getName() : user.getName());
        user.setEmail(data.getEmail() != null ? data.getEmail() : user.getEmail());

        if(data.getNewPassword() != null) {
            String hashedPassword = encoder.encode(data.getNewPassword());
            user.setPassword(data.getPassword());
        }

        repository.save(user);
        return DozzerMapper.parseObject(user, UserResponseDto.class);
    }

    /*
        - Implementar serviço de autenticação.
        - Implementar serviço de alteração de nível de usuário, havendo Adm Mestre, adm normal e usuário comum.
    */
}
