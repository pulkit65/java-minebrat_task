package com.java_minebrat_task.service;

import com.java_minebrat_task.entity.AuditLog;
import com.java_minebrat_task.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void record(String actionType, String description) {
        AuditLog log = AuditLog.builder()
                .actionType(actionType)
                .description(description)
                .build();
        auditLogRepository.save(log);
    }
}

