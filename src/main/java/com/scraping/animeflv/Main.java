package com.scraping.animeflv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
//        String nameAnime = "naruto";
//        String pageList = "1";
//        searchAnime(nameAnime, pageList);
        String id = "naruto";
        infoAnime(id);
//        String cap = "220";
//        links(cap, id);
    }
    public static void links(String cap, String id) throws IOException {
        String url = "https://www3.animeflv.net/ver/" + id + "-" + cap;
        Document document = Jsoup.connect(url).get();
        Elements rows = document.select("table.RTbl.Dwnl tbody tr");
        List<String> links = new ArrayList<>();
        for (Element row : rows) {
            Elements columns = row.select("td");
            String servidor = columns.get(0).text();
            String tamaño = columns.get(1).text();
            String formato = columns.get(2).text();
            String enlace = columns.get(3).select("a").attr("href");
            links.add(enlace);
            // Imprimir los datos extraídos
//            System.out.println("Servidor: " + servidor);
//            System.out.println("Tamaño: " + tamaño);
//            System.out.println("Formato: " + formato);
//            System.out.println("Enlace: " + enlace);
//            System.out.println("-------------------");
        }
        System.out.println(links);
    }

    public static void infoAnime(String id) throws IOException {
        String url = "https://www3.animeflv.net/anime/" + id;
        Document document = Jsoup.connect(url).get();
        Element h1 = document.selectFirst("h1.Title");
        String titulo = h1.text();

        Element figure = document.selectFirst("figure");
        String src = figure.select("img").attr("src");
        String img = "https://www3.animeflv.net" + src;

        Element descriptionDiv = document.selectFirst("div.Description");
        String descriptionText = descriptionDiv.text();

        Elements nvgnrs = document.select("nav.Nvgnrs a");
        List<String> genres = new ArrayList<>();
        for (Element nvg : nvgnrs) {
            genres.add(nvg.text());
        }

        Element spanFaTv = document.selectFirst("span.fa-tv");
        String statusAnime = spanFaTv.text();

        Element spanVtprmd = document.selectFirst("span.vtprmd");
        String votes = spanVtprmd.text();

        Element element = document.getElementsByClass("ListCaps").first();
        assert element != null;
        System.out.println(element);
//        Elements articles = element.select("ul.article.Anime");
//        System.out.println(articles);

    }

    public static void searchAnime(String nameAnime, String page) throws IOException {
//        String nameAnime = "boku";
//        String pageList = "1";
        String url = "https://www3.animeflv.net/browse?q=" + nameAnime + "&page=" + page;
        Document document = Jsoup.connect(url).get();
        Element element = document.getElementsByClass("ListAnimes AX Rows A03 C02 D02").first();
        assert element != null;
        Elements articles = element.select("article.Anime");
        Element paginationElement = document.getElementsByClass("pagination").first();
        assert paginationElement != null;
        Elements paginations = paginationElement.getElementsByTag("li");

        int countAnimes = 0;
        for (Element article : articles) {
            // Obtener el href del <a>
            String animeLink = article.select("a").attr("href");
            String[] resource = animeLink.split("/");
            String idAnime = resource[resource.length - 1];
            System.out.println("Id del anime: " + idAnime);

            // Obtener el src de la imagen
            String imageUrl = article.select("img").attr("src");
            System.out.println("URL de la imagen: " + imageUrl);

            // Obtener el título
            String title = article.select("h3.Title").text();
            System.out.println("Título: " + title);

            // Obtener la descripción
            String description = Objects.requireNonNull(article.select("div.Description > p").last()).text();
            System.out.println("Descripción: " + description);
            countAnimes += 1;
            System.out.println("-------------------------------------");
        }
        System.out.println("countAnimes: " + countAnimes);
        System.out.println();

        List<String> pageNumbers = new ArrayList<>();
        for (Element pagination : paginations) {
            // Obtener el href del <a> dentro del <li>
            String numberPage = pagination.select("a").text();
            // Verificar si el número es válido
            if (isNumber(page)) {
                pageNumbers.add(page);
            }
        }
        System.out.println("Lista de números de página: " + pageNumbers);
        // Calcular la longitud de la lista
        int length = pageNumbers.size();
        System.out.println("La longitud de pageNumbers es: " + length);
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
