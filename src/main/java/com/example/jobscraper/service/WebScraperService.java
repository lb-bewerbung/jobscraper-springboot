package com.example.jobscraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Database imports
import com.example.jobscraper.model.Job;
import com.example.jobscraper.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class WebScraperService {

    @Autowired
    private JobRepository jobRepository;

    // Scrape job links and job titles and store in MongodDb
    public String scrapeAndSaveJobs(String searchTerm) {
        List<Map<String, String>> jobLinksAndTitles = extractJoblinksAndJobtitles(searchTerm);

        List<Job> jobList = new ArrayList<>();

        for (Map<String, String> jobEntry : jobLinksAndTitles) {
            String jobTitle = jobEntry.get("jobtitle");
            String jobLink = jobEntry.get("joblink");

            // Check for pre-existing job entries
            if (!jobRepository.existsByJobLink(jobLink)) {
                jobList.add(new Job(jobTitle, jobLink, false));
            }
        }

        // Store job objects in MongoDb
        if (!jobList.isEmpty()) {
            jobRepository.saveAll(jobList);
            return "New jobs have been successfully stored";
        }
        return "No new jobs available";
    }

    public List<Map<String, String>> extractJoblinksAndJobtitles(String searchTerm) {
        List<Map<String, String>> jobLinksAndTitles = new ArrayList<>();
        String url = "https://www.<enter-webpage>" + searchTerm;

        url = null;

        /* Scraping job-webpage url
        *  Content has been removed in precaution of possible legal concerns
        *
        *
        * */

        return jobLinksAndTitles;
    }

    public void updateJobDetailsForUnsavedJobs() {
        List<Job> jobsToUpdate = jobRepository.findBySavedJobDetailsFalse();

        for (Job job : jobsToUpdate) {
            String jobUrl = job.getJobLink();

            saveJobDetails(jobUrl);

            try {
                Thread.sleep(1500); // throttle requests
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Extract job details from url and store in MongoDb
    public String saveJobDetails(String jobUrl) {
        Map<String, List<String>> jobDetails = extractJobDetails(jobUrl);

        Optional<Job> optionalJob = jobRepository.findByJobLink(jobUrl);

        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();

            job.setIntroduction(jobDetails.get("introduction"));
            job.setJobDescription(jobDetails.get("job_description"));
            job.setProfile(jobDetails.get("profile"));
            job.setBenefits(jobDetails.get("benefits"));
            job.setAdditionalInformation(jobDetails.get("additional_information"));
            job.setSavedJobDetails(true);

            jobRepository.save(job);
            return "job details have been successfully updated";
        } else {
            return "No job was found with given Url";
        }
    }

    public Map<String, List<String>> extractJobDetails(String jobUrl) {
        Map<String, List<String>> jobDetails = new HashMap<>();
        jobDetails.put("introduction", new ArrayList<>());
        jobDetails.put("job_description", new ArrayList<>());
        jobDetails.put("profile", new ArrayList<>());
        jobDetails.put("benefits", new ArrayList<>());
        jobDetails.put("additional_information", new ArrayList<>());

        // Selektoren für die verschiedenen Abschnitte
        Map<String, String> selectors = Map.of(
                "introduction", ".at-section-text-introduction",
                "job_description", ".at-section-text-description",
                "profile", ".at-section-text-profile",
                "benefits", ".at-section-text-benefits",
                "additional_information", ".at-section-text-additionalInformation"
        );

        try {
            Document doc = Jsoup.connect(jobUrl).get();

            for (Map.Entry<String, String> entry : selectors.entrySet()) {
                String key = entry.getKey();
                String selector = entry.getValue();
                Element section = doc.selectFirst(selector);

                if (section != null) {
                    Elements listItems = section.select("li");

                    // Speichert den gesamten Text für "introduction"
                    if (key.equals("introduction")) {
                        jobDetails.get(key).add(section.text().strip());
                    } else {
                        // Fügt alle Listenelemente hinzu
                        for (Element item : listItems) {
                            jobDetails.get(key).add(item.text().strip());
                        }
                        // Extrahiert Absätze mit <strong> und speichert den gesamten Text
                        Elements pTagsWithStrong = section.select("p:has(strong)");
                        for (Element pTag : pTagsWithStrong) {
                            jobDetails.get(key).add(pTag.text().strip());
                        }
                        // Fallback, falls keine <li> oder <strong> existieren
                        if (listItems.isEmpty() && pTagsWithStrong.isEmpty()) {
                            jobDetails.get(key).add(section.text().strip());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error scraping job details: " + e.getMessage());
        }

        return jobDetails;
    }

    public String deleteAllJobs() {
        jobRepository.deleteAll();
        return "All jobs have been successfully deleted";
    }
}