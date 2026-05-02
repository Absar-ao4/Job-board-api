package com.absar.jobboard.controller;

import com.absar.jobboard.model.Application;
import com.absar.jobboard.model.Job;
import com.absar.jobboard.model.User;
import com.absar.jobboard.repository.ApplicationRepository;
import com.absar.jobboard.repository.JobRepository;
import com.absar.jobboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // APPLY for a job
    @PostMapping
    public ResponseEntity<?> applyForJob(
            @RequestParam Long userId,
            @RequestParam Long jobId) {

        User applicant = userRepository.findById(userId)
                .orElse(null);
        if (applicant == null) {
            return ResponseEntity.badRequest()
                    .body("User not found");
        }

        if (!applicant.getRole().equals("APPLICANT")) {
            return ResponseEntity.badRequest()
                    .body("Only applicants can apply for jobs");
        }

        Job job = jobRepository.findById(jobId)
                .orElse(null);
        if (job == null) {
            return ResponseEntity.badRequest()
                    .body("Job not found");
        }

        Application application = new Application();
        application.setApplicant(applicant);
        application.setJob(job);
        application.setStatus("APPLIED");
        application.setAppliedAt(LocalDateTime.now());

        Application saved = applicationRepository.save(application);
        return ResponseEntity.ok(saved);
    }

    // GET all applications for a job
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsForJob(
            @PathVariable Long jobId) {
        List<Application> apps = applicationRepository.findAll()
                .stream()
                .filter(a -> a.getJob().getId().equals(jobId))
                .toList();
        return ResponseEntity.ok(apps);
    }

    // UPDATE application status (recruiter shortlists or rejects)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Application app = applicationRepository.findById(id)
                .orElse(null);
        if (app == null) {
            return ResponseEntity.notFound().build();
        }

        if (!status.equals("APPLIED") &&
                !status.equals("SHORTLISTED") &&
                !status.equals("REJECTED")) {
            return ResponseEntity.badRequest()
                    .body("Invalid status");
        }

        app.setStatus(status);
        applicationRepository.save(app);
        return ResponseEntity.ok(app);
    }
}