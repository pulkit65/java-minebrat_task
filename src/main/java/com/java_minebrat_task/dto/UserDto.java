package com.java_minebrat_task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        @NotBlank String username,
        @NotBlank String firstName,
        String lastName,
        @Email String email,
        String phone,
        String status,
        List<AddressDto> addresses
) {}