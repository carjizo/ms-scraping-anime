package com.scraping.animeflv.services.impl;

import com.scraping.animeflv.entities.AnimeInfo;
import com.scraping.animeflv.entities.AnimePagination;
import com.scraping.animeflv.services.AnimeService;
import com.scraping.animeflv.services.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private ScrapingService scrapingService;

    @Override
    public AnimePagination findAnimeByText(String arg, String page) throws IOException {
        return scrapingService.searchAnime(arg, page);
    }

    @Override
    public AnimeInfo info(String id) throws IOException {
        return scrapingService.infoAnime(id);
    }

    @Override
    public List<String> getLinks(String id, String cap) throws IOException {
        return scrapingService.links(id, cap);
    }

}
