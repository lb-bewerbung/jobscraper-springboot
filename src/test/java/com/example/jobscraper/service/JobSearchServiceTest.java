package com.example.jobscraper.service;

import com.example.jobscraper.model.Job;
import com.example.jobscraper.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
class JobSearchServiceTest {

    @Autowired
    private JobSearchService jobSearchService;

    @Autowired
    private JobRepository jobRepository;

/* das hier können wir verwenden wenn wir eine produktivdatenbank und eine testdatenbank haben */
//    @BeforeEach
//    void setUp() {
//        // Bereinigt die Sammlung, um sicherzustellen, dass wir immer bei null starten
//        jobRepository.deleteAll();
//
//        // Testdaten in die MongoDB einfügen
//        Job job1 = new Job();
//        job1.setJobTitle("Java Developer");
//        job1.setJobLink("https://example.com/job1");
//        job1.setIntroduction(Arrays.asList("Great opportunity", "Innovative team"));
//        job1.setJobDescription(Arrays.asList("Develop Java applications", "Work with Spring Boot"));
//        job1.setProfile(Arrays.asList("Experience with Java", "Knowledge of Spring"));
//        job1.setBenefits(Arrays.asList("Health insurance", "Remote work"));
//        job1.setAdditionalInformation(Arrays.asList("Relocation support"));
//
//        Job job2 = new Job();
//        job2.setJobTitle("Frontend Developer");
//        job2.setJobLink("https://example.com/job2");
//        job2.setIntroduction(Arrays.asList("Dynamic environment", "Career growth"));
//        job2.setJobDescription(Arrays.asList("Develop frontend applications", "Work with React"));
//        job2.setProfile(Arrays.asList("Experience with JavaScript", "Knowledge of React"));
//        job2.setBenefits(Arrays.asList("Flexible hours", "Learning support"));
//        job2.setAdditionalInformation(Arrays.asList("International team"));
//
//        // Speichere die Jobs in der Datenbank
//        jobRepository.saveAll(Arrays.asList(job1, job2));
//    }

    @Test
    void testFindJobsByTitle() {
        // Suche nur nach Jobs mit Titel, der "Java" enthält
        String jobTitle = "Java";
        List<Job> result = jobSearchService.findJobs(jobTitle, "", "", "", "", "", "");

        // Überprüfe, ob jedes Ergebnis den Suchbegriff "Java" im Jobtitel enthält
        assertFalse(result.isEmpty(), "Ergebnisliste sollte nicht leer sein.");

        for (Job job : result) {
            assertTrue(
                    job.getJobTitle().contains("Java"),
                    "Erwarteter Jobtitel sollte 'Java' enthalten, gefunden: " + job.getJobTitle()
            );
        }
    }


    @Test
    void testFindJobsByProfile() {
        // Suche nach Jobs mit "Java" im Profile
        String profile = "Java";
        List<Job> result = jobSearchService.findJobs("", "", "", "", profile, "", "");

        // Überprüfe, ob jedes Ergebnis den Suchbegriff "Java" im Jobtitel enthält
        assertFalse(result.isEmpty(), "Ergebnisliste sollte nicht leer sein.");

        for (Job job : result) {
            assertTrue(
                    job.getJobTitle().contains("Java"),
                    "Erwarteter Jobtitel sollte 'Java' enthalten, gefunden: " + job.getJobTitle()
            );
        }
    }

    @Test
    void testFindJobsWithMultipleCriteria() {
        // Suche nach Jobs, die im Titel und Profil das Stichwort "Java" enthalten
        String jobTitle = "Java";
        String profile = "Java";
        List<Job> result = jobSearchService.findJobs(jobTitle, "", "", "", profile, "", "");

        // Überprüfe, ob die Ergebnisliste nicht leer ist
        assertFalse(result.isEmpty(), "Ergebnisliste sollte nicht leer sein.");

        // Stelle sicher, dass jedes Ergebnis das Stichwort "Java" im Jobtitel und Profil enthält
        for (Job job : result) {
            assertTrue(
                    job.getJobTitle().contains("Java"),
                    "Jobtitel sollte 'Java' enthalten, gefunden: " + job.getJobTitle()
            );
            assertTrue(
                    job.getProfile().stream().anyMatch(p -> p.contains("Java")),
                    "Profil sollte 'Java' enthalten, gefunden: " + job.getProfile()
            );
        }
    }

    // Eine Testkonfiguration für den Service, da Spring ihn in diesem Fall nicht automatisch injiziert
    @TestConfiguration
    static class JobSearchServiceTestConfig {
        @Bean
        public JobSearchService jobSearchService(JobRepository jobRepository) {
            return new JobSearchService(jobRepository);
        }
    }
}
