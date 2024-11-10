package com.scraping.animeflv.services.impl;

import com.scraping.animeflv.entities.User;
import com.scraping.animeflv.repositories.UserRepository;
import com.scraping.animeflv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
