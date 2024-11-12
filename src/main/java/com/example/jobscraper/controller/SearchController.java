package com.example.jobscraper.controller;

import com.example.jobscraper.service.JobSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jobscraper.model.Job;
import com.example.jobscraper.repository.JobRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private JobRepository jobRepository;
    private final JobSearchService jobSearchService;

    @Autowired
    public SearchController(JobSearchService jobSearchService) {
        this.jobSearchService = jobSearchService;
    }

    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        List<Job> jobList = jobRepository.findAll();
        model.addAttribute("jobs", jobList);
        return "jobs"; // Thymeleaf template name (jobs.html)
    }


    // Methode für das Suchformular und die Anzeige der Ergebnisse
    @GetMapping("/search")
    public String searchJobs(@RequestParam(value = "jobTitle", required = false) String jobTitle,
                             @RequestParam(value = "jobLink", required = false) String jobLink,
                             @RequestParam(value = "introduction", required = false) String introduction,
                             @RequestParam(value = "jobDescription", required = false) String jobDescription,
                             @RequestParam(value = "profile", required = false) String profile,
                             @RequestParam(value = "benefits", required = false) String benefits,
                             @RequestParam(value = "additionalInformation", required = false) String additionalInformation,
                             Model model) {

        // Prüfen, ob alle Parameter null oder leer sind (keine Filter)
        boolean noSearchCriteria = (jobTitle == null || jobTitle.isEmpty()) &&
                (jobLink == null || jobLink.isEmpty()) &&
                (introduction == null || introduction.isEmpty()) &&
                (jobDescription == null || jobDescription.isEmpty()) &&
                (profile == null || profile.isEmpty()) &&
                (benefits == null || benefits.isEmpty()) &&
                (additionalInformation == null || additionalInformation.isEmpty());

        List<Job> jobList = new ArrayList<>(); // Leere Liste, falls keine Suche
        boolean searchPerformed = false;

        // Nur wenn Suchkriterien gesetzt sind, wird die Suche ausgeführt
        if (!noSearchCriteria) {
            jobList = jobSearchService.findJobs(jobTitle, jobLink, introduction, jobDescription, profile, benefits, additionalInformation);
            searchPerformed = true;
        }
        // Ergebnisse und Suchparameter an das Model übergeben
        model.addAttribute("jobs", jobList);

        // Felder sollen nach der Ausführung der Suche im Formular enthalten bleiben.
        model.addAttribute("jobTitle", jobTitle);
        model.addAttribute("jobLink", jobLink);
        model.addAttribute("introduction", introduction);
        model.addAttribute("jobDescription", jobDescription);
        model.addAttribute("profile", profile);
        model.addAttribute("benefits", benefits);
        model.addAttribute("additionalInformation", additionalInformation);
        model.addAttribute("searchPerformed", searchPerformed); // Zeigt an, ob eine Suche durchgeführt wurde

        return "search"; // Zeigt das Such-Template search.html an
    }
}

