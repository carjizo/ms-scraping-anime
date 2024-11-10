package com.scraping.animeflv.repositories;

import com.scraping.animeflv.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdIdent(String idIdent);
}
