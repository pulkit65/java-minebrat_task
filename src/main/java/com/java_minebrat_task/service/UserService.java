package com.java_minebrat_task.service;


import com.java_minebrat_task.dto.UserDto;
import com.java_minebrat_task.dto.mappers.UserMapper;
import com.java_minebrat_task.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final AuditLogRepository auditRepo;

    public Page<UserDto> getAllUsers(String search, Pageable pageable) {
        Page<User> page;
        if (search != null && !search.isBlank()) {
            page = userRepo.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable);
        } else {
            page = userRepo.findAll(pageable);
        }
        return page.map(UserMapper::toDto);
    }

    public UserDto getUserById(UUID id) {
        return userRepo.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public UserDto createUser(UserDto dto, String performedBy) {
        User user = UserMapper.toEntity(dto);
        userRepo.save(user);
        createAudit("USER", user.getId(), "CREATE", performedBy, user.getUsername());
        return UserMapper.toDto(user);
    }

    @Transactional
    public UserDto updateUser(UUID id, UserDto dto, String performedBy) {
        User existing = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setEmail(dto.email());
        existing.setPhone(dto.phone());
        existing.setStatus(UserStatus.valueOf(dto.status()));

        userRepo.save(existing);
        createAudit("USER", id, "UPDATE", performedBy, "Updated user");
        return UserMapper.toDto(existing);
    }

    @Transactional
    public void deleteUser(UUID id, String performedBy) {
        if (!userRepo.existsById(id)) throw new EntityNotFoundException("User not found");
        userRepo.deleteById(id);
        createAudit("USER", id, "DELETE", performedBy, "User deleted");
    }

    private void createAudit(String entityType, UUID entityId, String action, String performedBy, String details) {
        AuditLog log = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .performedBy(performedBy)
                .details(details)
                .build();
        auditRepo.save(log);
    }
}

