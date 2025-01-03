package com.example.jobscraper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "jobs") // MongoDB collection
public class Job {

    @Id
    private String id;

    private String jobTitle;
    private String jobLink;
    private List<String> introduction;
    private List<String> jobDescription;
    private List<String> profile;
    private List<String> benefits;
    private List<String> additionalInformation;
    private Boolean savedJobDetails;

    // Getter and setter

    public Job(String jobTitle, String jobLink, Boolean savedJobDetails) {
        this.jobTitle = jobTitle;
        this.jobLink = jobLink;
        this.savedJobDetails = savedJobDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) { this.jobLink = jobLink; }

    public List<String> getIntroduction() {
        return introduction;
    }

    public void setIntroduction(List<String> introduction) {
        this.introduction = introduction;
    }

    public List<String> getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(List<String> jobDescription) {
        this.jobDescription = jobDescription;
    }

    public List<String> getProfile() {
        return profile;
    }

    public void setProfile(List<String> profile) {
        this.profile = profile;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(List<String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setSavedJobDetails(Boolean savedJobDetails) {
        this.savedJobDetails = savedJobDetails;
    }

}

