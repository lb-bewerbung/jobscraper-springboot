package com.example.jobscraper.controller;

import com.example.jobscraper.service.WebScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WebScraperRestController {
    @Autowired
    private WebScraperService webScraperService;

    @GetMapping("/scrape")
    public String scrapeWebsite(@RequestParam String url) {
        return webScraperService.scrapeWebsite(url);
    }

    @GetMapping("/extractjoblinks")
    public List<Map<String, String>> extractJoblinksAndJobtitles(@RequestParam String searchTerm) {
        return webScraperService.extractJoblinksAndJobtitles(searchTerm);
    }

    @GetMapping("/scrapeandsavejobs")
    public String scrapeAndSaveJobs(@RequestParam String searchTerm) {
        return webScraperService.scrapeAndSaveJobs(searchTerm);
    }

    @DeleteMapping("/deleteAllJobs")
    public String deleteAllJobs() {
        return webScraperService.deleteAllJobs();
    }
}