package com.scraping.animeflv.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeInfo {

    private String title;
    private String imagenUrl;
    private String description;
    private List<String> genres;
    private String status;
    private String votes;
}
