//package com.example.jobscraper.service;
//
//import com.example.jobscraper.repository.JobRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.*;
//
//// Mock
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//
//
//@SpringBootTest
//class WebScraperServiceTest {
//
//    @Autowired
//    private WebScraperService webScraperService;
//
//    @Test
//    void testScrapeWebsiteSuccess() {
//        // Gebe hier eine zuverlässige URL an, die nicht häufig geändert wird
//        String url = "https://example.com";  // Beispiel-URL
//
//        // Rufe die Methode scrapeWebsite auf und speichere das Ergebnis
//        String result = webScraperService.scrapeWebsite(url);
//
//        // Überprüfe, ob das Ergebnis einen bestimmten Text enthält, z.B. den Titel der Seite
//        assertTrue(result.contains("Example Domain"));
//    }
//
//    @Test
//    void testScrapeWebsiteInvalidUrl() {
//        // Verwende eine ungültige URL
//        String url = "https://invalid-url";
//
//        // Überprüfe, ob die Methode eine Fehlernachricht zurückgibt
//        String result = webScraperService.scrapeWebsite(url);
//        assertTrue(result.contains("Error scraping website"));
//    }
//
//    @MockBean
//    private WebScraperService webScraperServiceMock;
//
//    @Test
//    void testScrapeWebsiteWithMock() {
//        // Mock das Verhalten der Methode scrapeWebsite
//        String fakeContent = "Fake website content for testing";
//        Mockito.when(webScraperServiceMock.scrapeWebsite("https://example.com"))
//                .thenReturn(fakeContent);
//
//        // Rufe die Methode auf und überprüfe das gemockte Ergebnis
//        String result = webScraperServiceMock.scrapeWebsite("https://example.com");
//        assertEquals("Fake website content for testing", result);
//    }
//}