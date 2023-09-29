package com.menufoods.controllers;

import com.menufoods.domain.dto.auth.LoginDTO;
import com.menufoods.domain.dto.auth.LoginResponseDTO;
import com.menufoods.domain.model.User;
import com.menufoods.services.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO request) throws Exception {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok().body(new LoginResponseDTO(token));
        } catch (Exception e) {
            throw new Exception("Email e/ou senha incorretos!");
        }

    }
}
