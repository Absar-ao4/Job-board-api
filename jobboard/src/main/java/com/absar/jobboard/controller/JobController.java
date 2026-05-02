package com.absar.jobboard.controller;

import com.absar.jobboard.model.Job;
import com.absar.jobboard.model.User;
import com.absar.jobboard.repository.JobRepository;
import com.absar.jobboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE job
    @PostMapping
    public ResponseEntity<?> createJob(
            @RequestParam Long userId,
            @RequestBody Job job) {

        User poster = userRepository.findById(userId)
                .orElse(null);

        if (poster == null) {
            return ResponseEntity.badRequest()
                    .body("User not found");
        }

        if (!poster.getRole().equals("RECRUITER")) {
            return ResponseEntity.badRequest()
                    .body("Only recruiters can post jobs");
        }

        job.setPostedBy(poster);
        job.setCreatedAt(LocalDateTime.now());
        Job saved = jobRepository.save(job);
        return ResponseEntity.ok(saved);
    }

    // GET all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }

    // GET job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        if (!jobRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jobRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}