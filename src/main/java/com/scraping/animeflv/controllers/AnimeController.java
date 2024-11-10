package com.scraping.animeflv.controllers;

import com.scraping.animeflv.dtos.LinksRequestDTO;
import com.scraping.animeflv.dtos.SearchAnimeRequestDTO;
import com.scraping.animeflv.entities.AnimeInfo;
import com.scraping.animeflv.entities.AnimePagination;
import com.scraping.animeflv.services.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchAnimeRequestDTO searchAnimeRequestDTO) throws IOException {
        AnimePagination animePagination = animeService.findAnimeByText(searchAnimeRequestDTO.getText(), searchAnimeRequestDTO.getPage());
        return ResponseEntity.ok(animePagination);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> infoAnime(@PathVariable String id) throws IOException {
        AnimeInfo anime = animeService.info(id);
        return ResponseEntity.ok(anime);
    }

    @PostMapping("/links")
    public ResponseEntity<?> links(@RequestBody LinksRequestDTO linksRequestDTO) throws IOException {
        List<String> links = animeService.getLinks(linksRequestDTO.getId(), linksRequestDTO.getCap());
        return ResponseEntity.ok(links);
    }
}
