package com.java_minebrat_task.dto;


import java.util.*;
import jakarta.validation.constraints.*;

public record AddressDto(
        UUID id,
        @NotBlank String line1,
        String line2,
        @NotBlank String city,
        String state,
        String postalCode,
        @NotBlank String country,
        @NotBlank String type
) {}

