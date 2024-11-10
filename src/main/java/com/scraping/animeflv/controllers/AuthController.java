package com.scraping.animeflv.controllers;

import com.scraping.animeflv.entities.User;
import com.scraping.animeflv.services.AuthService;
import com.scraping.animeflv.dtos.RequestLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<String> addUser(@RequestBody RequestLoginDTO requestLoginDTO) throws Exception {
        User user = User.builder()
                .idIdent(requestLoginDTO.getIdIdent())
                .password(requestLoginDTO.getPassword())
                .role(requestLoginDTO.getRole())
                .status(true)
                .build();
        return new ResponseEntity<>(authService.register(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<HashMap<String, String>> login(@RequestBody RequestLoginDTO requestLoginDTO) throws Exception {
        HashMap<String, String> login = authService.login(requestLoginDTO);
        if (login.containsKey("jwt")) {
            return new ResponseEntity<>(authService.login(requestLoginDTO), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(authService.login(requestLoginDTO), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/validate-token/{jwt}")
    public ResponseEntity<?> search(@PathVariable String jwt) throws Exception {
        return ResponseEntity.ok(authService.verifyToken(jwt));
    }
}
