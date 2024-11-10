package com.scraping.animeflv.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Anime {

    private String id;
    private String imagenUrl;
    private String title;
    private String description;
}
