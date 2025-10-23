package com.java_minebrat_task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "csv_import_jobs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CSVImportJob {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String status; // COMPLETED, PARTIAL, FAILED, RUNNING

    private int totalRows;
    private int successRows;
    private int failedRows;

    @CreationTimestamp
    private Instant startedAt;

    private Instant completedAt;

    @OneToMany(mappedBy = "importJob", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CSVImportError> errors = new ArrayList<>();
}

