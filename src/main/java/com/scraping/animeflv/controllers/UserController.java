package com.scraping.animeflv.controllers;

import com.scraping.animeflv.dtos.UserResponseDTO;
import com.scraping.animeflv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    private ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> userDTOList = userService.findAll()
                .stream()
                .map(user -> UserResponseDTO.builder()
                        .idIdent(user.getIdIdent())
                        .role(user.getRole())
                        .status(user.isStatus())
                        .build())
                .toList();

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }
}
