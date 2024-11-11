package com.example.jobscraper.repository;

import com.example.jobscraper.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


// Erweitert das MongoRepository für CRUD-Operationen
public interface JobRepository extends MongoRepository<Job, String> {
    Optional<Job> findByJobLink(String jobLink);

    List<Job> findBySavedJobDetailsFalse();

    boolean existsByJobLink(String jobLink);
}
