package com.scraping.animeflv.services;

import com.scraping.animeflv.entities.User;
import com.scraping.animeflv.dtos.RequestLoginDTO;

import java.util.HashMap;

public interface AuthService {

    public HashMap<String, String> login(RequestLoginDTO requestLoginDTO) throws Exception;
    public String register(User user) throws Exception;
    public boolean verifyToken(String jwt) throws Exception;
}
