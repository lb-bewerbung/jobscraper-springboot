package com.example.jobscraper; // Dein Paket

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class WebScraper {

    public static void main(String[] args) {
        try {
            // Webseite laden
            Document doc = Jsoup.connect("https://example.com").get();

            // Titel der Seite extrahieren
            String title = doc.title();
            System.out.println("Titel der Seite: " + title);

            // Alle Links auf der Seite extrahieren
            for (Element link : doc.select("a[href]")) {
                System.out.println("Link: " + link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
