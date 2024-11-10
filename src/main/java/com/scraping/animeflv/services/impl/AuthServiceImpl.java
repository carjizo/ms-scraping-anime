package com.scraping.animeflv.services.impl;

import com.scraping.animeflv.entities.User;
import com.scraping.animeflv.repositories.UserRepository;
import com.scraping.animeflv.services.AuthService;
import com.scraping.animeflv.services.JWTUtilityService;
import com.scraping.animeflv.dtos.RequestLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Override
    public HashMap<String, String> login(RequestLoginDTO requestLoginDTO) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<User> user = userRepository.findByIdIdent(requestLoginDTO.getIdIdent());
            if (user.isEmpty()) {
                jwt.put("error", "User not registered!");
                return jwt;
            }
            if (verifyPassword(requestLoginDTO.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getIdUser()));
            } else {
                jwt.put("error", "Authentication failed");
            }
            return jwt;
        } catch (IllegalArgumentException e) {
            System.err.println("Error generating JWT: " + e.getMessage());
            throw new Exception("Error generating JWT", e);
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.toString());
            throw new Exception("Unknown error", e);
        }
    }

    @Override
    public String register(User user) throws Exception {
        String response;
        try {
            List<User> getAllUsers = (List<User>) userRepository.findAll();

            for (User existingUser : getAllUsers) {
                if (existingUser.getIdIdent().equals(user.getIdIdent())) {
                    response = "Identity already exists!";
                    return response;
                }
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            response = "User created successfully!";
            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean verifyToken(String jwt) throws Exception {
        try {
            return jwtUtilityService.verifyJWT(jwt);
        } catch (Exception e) {
            return false;
        }
    }


    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
