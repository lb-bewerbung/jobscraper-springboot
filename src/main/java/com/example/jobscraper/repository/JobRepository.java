package com.example.jobscraper.repository;

import com.example.jobscraper.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


// Erweitert das MongoRepository für CRUD-Operationen
public interface JobRepository extends MongoRepository<Job, String> {
    Optional<Job> findByJobLink(String jobLink);

    List<Job> findBySavedJobDetailsFalse();

    boolean existsByJobLink(String jobLink);

    @Query("{ " +
            "'jobTitle': { $regex: ?0, $options: 'i' }, " +
            "'jobLink': { $regex: ?1, $options: 'i' }, " +
            "'introduction': { $regex: ?2, $options: 'i' }, " +
            "'jobDescription': { $regex: ?3, $options: 'i' }, " +
            "'profile': { $regex: ?4, $options: 'i' }, " +
            "'benefits': { $regex: ?5, $options: 'i' }, " +
            "'additionalInformation': { $regex: ?6, $options: 'i' } " +
            "}")
    List<Job> findJobsByCriteria(String jobTitle, String jobLink, String introduction,
                                 String jobDescription, String profile,
                                 String benefits, String additionalInformation);
}
