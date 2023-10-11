package com.menufoods.services.token;

import com.menufoods.domain.model.User;

import java.time.Instant;

public interface ITokenService {

    String generateToken(User user);

    Instant genExpirationDate();

    String validateToken(String token);
}
