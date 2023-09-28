package com.menufoods.services.token;

import com.menufoods.model.entities.User.User;

import java.time.Instant;

public interface iTokenService {

    String generateToken(User user);

    Instant genExpirationDate();

    String validateToken(String token);
}
