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

    @GetMapping("/extractjoblinks")
    public List<Map<String, String>> extractJoblinksAndJobtitles(@RequestParam String searchTerm) {
        return webScraperService.extractJoblinksAndJobtitles(searchTerm);
    }

    @GetMapping("/scrapeandsavejobs")
    public String scrapeAndSaveJobs(@RequestParam String searchTerm) {
        return webScraperService.scrapeAndSaveJobs(searchTerm);
    }

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
            return "Job details have been successfully updated";
        } catch (Exception e) {
            return "Error while updating job details: " + e.getMessage();
        }
    }

    @GetMapping("/scrapejobsforsearchterm")
    public String scrapeAndSaveAndUpdateJobs(@RequestParam String searchTerm) {
        try {
            // 1. Collect joblinks of new jobs
            webScraperService.scrapeAndSaveJobs(searchTerm);

            // 2. Scrape job details for new jobs
            webScraperService.updateJobDetailsForUnsavedJobs();

            return "New Jobs have been updated successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @DeleteMapping("/deleteAllJobs")
    public String deleteAllJobs() {
        return webScraperService.deleteAllJobs();
    }
}