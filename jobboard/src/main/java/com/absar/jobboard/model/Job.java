package com.absar.jobboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;

    private LocalDateTime createdAt;
}