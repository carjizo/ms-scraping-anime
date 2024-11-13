package com.scraping.animeflv.services;

import com.scraping.animeflv.entities.AnimeInfo;
import com.scraping.animeflv.entities.AnimePagination;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface AnimeService {

    AnimePagination findAnimeByText(String arg, String page) throws IOException;

    AnimeInfo info(String id) throws IOException;

    HashMap<String, Object> getLinks(String id, String cap) throws IOException;
}
