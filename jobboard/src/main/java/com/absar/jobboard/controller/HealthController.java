package com.absar.jobboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "Job Board API is running successfully. Use Postman to test endpoints such as /auth/register, /auth/login, /jobs, /users, and /applications.";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}