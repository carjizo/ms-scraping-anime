package com.scraping.animeflv.services;

import com.scraping.animeflv.entities.User;
import com.scraping.animeflv.dtos.RequestLoginDTO;

import java.util.HashMap;

public interface AuthService {

    public HashMap<String, Object> login(RequestLoginDTO requestLoginDTO) throws Exception;
    public HashMap<String, Object>  register(RequestLoginDTO requestLoginDTO) throws Exception;
    public HashMap<String, Object> verifyToken(String jwt) throws Exception;
    public HashMap<String, Object> refreshToken(String jwt) throws Exception;
}
