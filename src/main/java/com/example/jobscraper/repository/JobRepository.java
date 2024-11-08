package com.example.jobscraper.repository;

import com.example.jobscraper.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

// Erweitert das MongoRepository für CRUD-Operationen
public interface JobRepository extends MongoRepository<Job, String> {
    // Hier können benutzerdefinierte Abfragen eingefügt werden
}
