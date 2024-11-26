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

        // Prepare search parameters for db-query | replace null with empty string to avoid errors
        jobTitle = jobTitle != null ? jobTitle : "";
        jobLink = jobLink != null ? jobLink : "";
        introduction = introduction != null ? introduction : "";
        jobDescription = jobDescription != null ? jobDescription : "";
        profile = profile != null ? profile : "";
        benefits = benefits != null ? benefits : "";
        additionalInformation = additionalInformation != null ? additionalInformation : "";

        // Use jobRepository to query
        return jobRepository.findJobsByCriteria(jobTitle, jobLink, introduction, jobDescription, profile, benefits, additionalInformation);
    }
}
