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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Database imports
import com.example.jobscraper.model.Job;
import com.example.jobscraper.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Service // Kennzeichnet diese Klasse als Spring Service
public class WebScraperService {

    @Autowired
    private JobRepository jobRepository;  // Injektion des Repositories

    public String scrapeWebsite(String url) {
        try {
            // Verbindet sich mit der Website und lädt die HTML-Seite
            Document doc = Jsoup.connect(url).get();

            // Extrahiert den Titel der Webseite
            String title = doc.title();

            // Extrahiert alle Links auf der Seite
            StringBuilder links = new StringBuilder();
            for (Element link : doc.select("a[href]")) {
                links.append(link.attr("href")).append("\n");
            }

            return "Title: " + title + "\nLinks:\n" + links.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error scraping website: " + e.getMessage();
        }
    }

    // Diese Methode ruft die extractJoblinksAndJobtitles auf und speichert die Ergebnisse in MongoDB
    public String scrapeAndSaveJobs(String searchTerm) {
        List<Map<String, String>> jobLinksAndTitles = extractJoblinksAndJobtitles(searchTerm);

        // Wandeln die extrahierten Joblinks und Jobtitel in Job-Objekte um
        List<Job> jobList = new ArrayList<>();

        // Iteriere durch die extrahierten Job-Links und Titel
        for (Map<String, String> jobEntry : jobLinksAndTitles) {
            String jobTitle = jobEntry.get("jobtitle");
            String jobLink = jobEntry.get("joblink");

            // Überprüfe, ob der Joblink schon existiert
            if (!jobRepository.existsByJobLink(jobLink)) {
                // Erstelle ein Job-Objekt und füge es der Liste hinzu, wenn der Joblink nicht existiert
                jobList.add(new Job(jobTitle, jobLink, false));
            }
        }

        // Speichern der Job-Objekte in MongoDB
        if (!jobList.isEmpty()) {
            jobRepository.saveAll(jobList);
            return "Neue Jobs wurden erfolgreich gespeichert!";
        }

        return "Keine neuen Jobs zum Speichern!";
    }

    public List<Map<String, String>> extractJoblinksAndJobtitles(String searchTerm) {
        List<Map<String, String>> jobLinksAndTitles = new ArrayList<>();
        String url = "https://www.<enter-webpage>" + searchTerm;

        url = null;

        /*Scraping job-webpage url
        * Content has been removed in precaution of legal reasons
        *
        *
        * */

        return jobLinksAndTitles;
    }

    public void updateJobDetailsForUnsavedJobs() {
        // 1. Finde alle Jobs, bei denen savedJobDetails == false
        List<Job> jobsToUpdate = jobRepository.findBySavedJobDetailsFalse();

        // 2. Rufe für die ersten zwei Jobs die Details ab
        int count = 0;
        for (Job job : jobsToUpdate) {
//            if (count >= 2) {
//                break;  // Beende die Schleife nach zwei Jobs
//            }

            String jobUrl = job.getJobLink(); // Job-Link aus der Datenbank holen

            // 3. Rufe die saveJobDetails-Methode für jede URL auf
            saveJobDetails(jobUrl);

            count++;  // Zähler erhöhen

            try {
                Thread.sleep(1500);  // Pause von 1 Sekunde zwischen den Scrapes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // Diese Methode extrahiert die Jobdetails und speichert sie in der Datenbank
    public String saveJobDetails(String jobUrl) {
        // 1. Extrahiere Jobdetails von der URL
        Map<String, List<String>> jobDetails = extractJobDetails(jobUrl);

        // 2. Finde den Job basierend auf der jobUrl in der Datenbank
        Optional<Job> optionalJob = jobRepository.findByJobLink(jobUrl);

        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();

            // 3. Ergänze die neuen Details
            job.setIntroduction(jobDetails.get("introduction"));
            job.setJobDescription(jobDetails.get("job_description"));
            job.setProfile(jobDetails.get("profile"));
            job.setBenefits(jobDetails.get("benefits"));
            job.setAdditionalInformation(jobDetails.get("additional_information"));
            job.setSavedJobDetails(true);

            // 4. Speichere den aktualisierten Job in der Datenbank
            jobRepository.save(job);
            return "Jobdetails wurden erfolgreich aktualisiert!";
        } else {
            return "Job mit der angegebenen URL wurde nicht gefunden.";
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
        return "Alle Jobs wurden erfolgreich gelöscht!";
    }
}