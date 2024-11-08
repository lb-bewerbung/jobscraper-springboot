package com.example.jobscraper.controller;

import com.example.jobscraper.service.WebScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebScraperController {

    @Autowired
    private WebScraperService webScraperService; // WebScraperService wird injiziert

    @GetMapping("/scrape")
    public String scrapeWebsite(@RequestParam String url) {
        return webScraperService.scrapeWebsite(url);
    }

    @GetMapping("/scrapestepstonesp")
    public String scrapeStepstoneSearchPage(@RequestParam String url) {
        return webScraperService.scrapeStepstoneSearchPage(url);
    }
}
