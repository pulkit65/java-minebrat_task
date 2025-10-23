package com.java_minebrat_task.controller;


import com.java_minebrat_task.dto.CsvImportResult;
import com.java_minebrat_task.service.CSVImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users/import")
@RequiredArgsConstructor
public class CSVImportController {

    private final CSVImportService importService;

    @PostMapping
    public ResponseEntity<CsvImportResult> importUsers(@RequestParam("file") MultipartFile file,
                                                       Principal principal) {
        CsvImportResult result = importService.importCsv(file, principal.getName());
        return ResponseEntity.ok(result);
    }
}

