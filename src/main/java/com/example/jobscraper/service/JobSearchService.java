package com.example.jobscraper.service;

import com.example.jobscraper.model.Job;
import com.example.jobscraper.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSearchService {

    private final JobRepository jobRepository;

    @Autowired
    public JobSearchService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findJobs(String jobTitle, String jobLink, String introduction,
                              String jobDescription, String profile,
                              String benefits, String additionalInformation) {

        // Leere Strings statt `null`-Werten verwenden, um alle Dokumente zu matchen, falls ein Feld nicht gesetzt ist
        jobTitle = jobTitle != null ? jobTitle : "";
        jobLink = jobLink != null ? jobLink : "";
        introduction = introduction != null ? introduction : "";
        jobDescription = jobDescription != null ? jobDescription : "";
        profile = profile != null ? profile : "";
        benefits = benefits != null ? benefits : "";
        additionalInformation = additionalInformation != null ? additionalInformation : "";

        // Verwende das Repository, um die Abfrage auszuführen
        return jobRepository.findJobsByCriteria(jobTitle, jobLink, introduction, jobDescription, profile, benefits, additionalInformation);
    }
}
