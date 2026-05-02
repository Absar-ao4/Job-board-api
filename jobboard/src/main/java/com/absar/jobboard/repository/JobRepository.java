package com.absar.jobboard.repository;

import com.absar.jobboard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}