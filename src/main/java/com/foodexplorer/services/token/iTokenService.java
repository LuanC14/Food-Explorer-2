package com.foodexplorer.services.token;

import com.foodexplorer.model.entities.User;

import java.time.Instant;

public interface iTokenService {

     String generateToken(User user);

     Instant genExpirationDate();

    String validateToken(String token);
}
