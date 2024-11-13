package com.scraping.animeflv.services.impl;

import com.scraping.animeflv.config.Constants;
import com.scraping.animeflv.entities.Anime;
import com.scraping.animeflv.entities.AnimeInfo;
import com.scraping.animeflv.entities.AnimePagination;
import com.scraping.animeflv.services.ScrapingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ScrapingServiceImpl implements ScrapingService {

    @Override
    public AnimePagination searchAnime(String arg, String page) throws IOException{
        String url = Constants.URL_ANIME_FLV + Constants.RESOURCE_SEARCH_ANIME + arg + Constants.IDENTITY_PAGE + page;
        Document document = Jsoup.connect(url).get();
        Element element = document.getElementsByClass("ListAnimes AX Rows A03 C02 D02").first();
        assert element != null;
        Elements articles = element.select("article.Anime");
        Element paginationElement = document.getElementsByClass("pagination").first();
        assert paginationElement != null;
        Elements paginations = paginationElement.getElementsByTag("li");

        int countAnimes = 0;
        List<Anime> animeList = new ArrayList<>();
        for (Element article : articles) {
            // Obtener el href del <a>
            String animeLink = article.select("a").attr("href");
            String[] resource = animeLink.split("/");
            String idAnime = resource[resource.length - 1];

            // Obtener el src de la imagen
            String imageUrl = article.select("img").attr("src");

            // Obtener el título
            String title = article.select("h3.Title").text();

            // Obtener la descripción
            String description = Objects.requireNonNull(article.select("div.Description > p").last()).text();
            Anime anime = new Anime();
            anime.setId(idAnime);
            anime.setImagenUrl(imageUrl);
            anime.setTitle(title);
            anime.setDescription(description);

            animeList.add(anime);

            countAnimes += 1;
        }

        List<String> pageNumbers = new ArrayList<>();
        for (Element pagination : paginations) {
            // Obtener el href del <a> dentro del <li>
            String numberPage = pagination.select("a").text();
            // Verificar si el número es válido
            if (isNumber(numberPage)) {
                pageNumbers.add(numberPage);
            }
        }
        // Calcular la longitud de la lista
        int length = pageNumbers.size();
        AnimePagination animePagination = new AnimePagination();
        animePagination.setAnimes(animeList);
        animePagination.setPages(length);
        return animePagination;
    }

    @Override
    public AnimeInfo infoAnime(String id) throws IOException {
        String url = Constants.URL_ANIME_FLV + Constants.RESOURCE_INFO_ANIME  + "/" + id;
        Document document = Jsoup.connect(url).get();
        Element h1 = document.selectFirst("h1.Title");
        String title = h1.text();

        Element figure = document.selectFirst("figure");
        String src = figure.select("img").attr("src");
        String img = "https://www3.animeflv.net" + src;

        Element descriptionDiv = document.selectFirst("div.Description");
        String description = descriptionDiv.text();

        Elements nvgnrs = document.select("nav.Nvgnrs a");
        List<String> genres = new ArrayList<>();
        for (Element nvg : nvgnrs) {
            genres.add(nvg.text());
        }

        Element spanFaTv = document.selectFirst("span.fa-tv");
        String status = spanFaTv.text();

        Element spanVtprmd = document.selectFirst("span.vtprmd");
        String votes = spanVtprmd.text();

        AnimeInfo anime = new AnimeInfo();
        anime.setTitle(title);
        anime.setImagenUrl(img);
        anime.setDescription(description);
        anime.setGenres(genres);
        anime.setVotes(votes);
        anime.setStatus(status);

        return anime;
    }

    @Override
    public List<String> links(String id, String cap) {
        List<String> links = new ArrayList<>();
        try {
            String idCap = "/" + id + "-" + cap;
            String url = Constants.URL_ANIME_FLV + Constants.RESOURCE_LINKS + idCap;
            Document document = Jsoup.connect(url).get();
            Elements rows = document.select("table.RTbl.Dwnl tbody tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                String link = columns.get(3).select("a").attr("href");
                links.add(link);
            }

            return links;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
//            throw new RuntimeException(e);
            return links;
        }

    }

    public static boolean isNumber(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ignored) {
        }
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

}
