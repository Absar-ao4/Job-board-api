package com.absar.jobboard.repository;

import com.absar.jobboard.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}