package com.example.jobscraper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")  // Die Sammlung in MongoDB
public class Job {

    @Id
    private String id;  // MongoDB verwendet standardmäßig `_id` als Primärschlüssel

    private String jobTitle;
    private String jobLink;

    // Getter und Setter

    public Job(String jobTitle, String jobLink) {
        this.jobTitle = jobTitle;
        this.jobLink = jobLink;
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

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }
}
