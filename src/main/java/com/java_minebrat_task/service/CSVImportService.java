package com.java_minebrat_task.service;


import com.java_minebrat_task.dto.CsvImportResult;
import com.java_minebrat_task.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CsvImportService {

    private final UserRepository userRepo;
    private final AuditLogRepository auditRepo;

    @Transactional
    public CsvImportResult importCsv(MultipartFile file, String performedBy) {
        int total = 0, success = 0;
        List<String> errors = new ArrayList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : parser) {
                total++;
                try {
                    User user = new User();
                    user.setUsername(record.get("username"));
                    user.setFirstName(record.get("firstName"));
                    user.setLastName(record.get("lastName"));
                    user.setEmail(record.get("email"));
                    user.setPhone(record.get("phone"));
                    userRepo.save(user);
                    success++;
                } catch (Exception e) {
                    errors.add("Row " + record.getRecordNumber() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }

        CsvImportResult result = new CsvImportResult(UUID.randomUUID(), total, success, errors);
        auditRepo.save(result.toAudit(performedBy));
        return result;
    }
}

