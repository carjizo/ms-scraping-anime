package com.scraping.animeflv.services;

import com.scraping.animeflv.entities.AnimeInfo;
import com.scraping.animeflv.entities.AnimePagination;

import java.io.IOException;
import java.util.List;

public interface ScrapingService {

    AnimePagination searchAnime(String arg, String page) throws IOException;

    AnimeInfo infoAnime(String id) throws IOException;

    List<String> links(String id, String cap) throws IOException;
}
