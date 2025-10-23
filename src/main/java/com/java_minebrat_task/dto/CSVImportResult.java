package com.java_minebrat_task.dto;

import com.java_minebrat_task.entity.AuditLog;

import java.time.Instant;
import java.util.*;

public record CsvImportResult(
        UUID importId,
        int totalRows,
        int successRows,
        List<String> errors
) {
    public AuditLog toAudit(String performedBy) {
        return AuditLog.builder()
                .entityType("CSV_IMPORT")
                .entityId(importId)
                .action("IMPORT")
                .performedBy(performedBy)
                .details("Imported " + successRows + "/" + totalRows + " rows with " + errors.size() + " errors")
                .timestamp(Instant.now())
                .build();
    }
}

