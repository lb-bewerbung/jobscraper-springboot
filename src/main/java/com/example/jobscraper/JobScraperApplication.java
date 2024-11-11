package com.example.jobscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class JobScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobScraperApplication.class, args);
    }

}

@RestController
class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Willkommen | Job Scraper";
    }
}