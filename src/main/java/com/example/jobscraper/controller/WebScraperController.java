package com.example.jobscraper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.jobscraper.model.Job;
import com.example.jobscraper.repository.JobRepository;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class WebScraperController {
    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        List<Job> jobList = jobRepository.findAll();
        model.addAttribute("jobs", jobList);
        return "jobs"; // Thymeleaf template name (jobs.html)
    }
}

