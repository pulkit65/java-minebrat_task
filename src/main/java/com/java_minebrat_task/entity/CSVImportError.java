package com.java_minebrat_task.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "csv_import_errors")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CsvImportError {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "import_job_id", nullable = false)
    private CsvImportJob importJob;

    @Column(nullable = false)
    private int rowNumber;

    @Lob
    private String errorMessage;
}

