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

    // Neue Route zum Extrahieren von Jobdetails
    @GetMapping("/extractjobdetails")
    public Map<String, List<String>> extractJobDetails(@RequestParam String jobUrl) {
        return webScraperService.extractJobDetails(jobUrl);
    }

    @GetMapping("/savejobdetails")
    public String saveJobDetails(@RequestParam String jobUrl) {
        return webScraperService.saveJobDetails(jobUrl);
    }

    @GetMapping("/updatejobdetails")
    public String updateJobDetailsForUnsavedJobs() {
        try {
            webScraperService.updateJobDetailsForUnsavedJobs();
            return "Jobdetails wurden erfolgreich aktualisiert!";
        } catch (Exception e) {
            return "Fehler beim Aktualisieren der Jobdetails: " + e.getMessage();
        }
    }

    @DeleteMapping("/deleteAllJobs")
    public String deleteAllJobs() {
        return webScraperService.deleteAllJobs();
    }
}