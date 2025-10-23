package com.java_minebrat_task.dto;


import com.java_minebrat_task.dto.*;
import com.java_minebrat_task.entity.*;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus().name(),
                user.getAddresses().stream().map(UserMapper::toDto).collect(Collectors.toList())
        );
    }

    public static AddressDto toDto(Address a) {
        return new AddressDto(
                a.getId(),
                a.getLine1(),
                a.getLine2(),
                a.getCity(),
                a.getState(),
                a.getPostalCode(),
                a.getCountry(),
                a.getType().name()
        );
    }

    public static User toEntity(UserDto dto) {
        User user = User.builder()
                .id(dto.id())
                .username(dto.username())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .phone(dto.phone())
                .status(UserStatus.valueOf(dto.status()))
                .build();

        if (dto.addresses() != null) {
            dto.addresses().forEach(ad -> {
                Address address = toEntity(ad);
                user.addAddress(address);
            });
        }
        return user;
    }

    public static Address toEntity(AddressDto dto) {
        return Address.builder()
                .id(dto.id())
                .line1(dto.line1())
                .line2(dto.line2())
                .city(dto.city())
                .state(dto.state())
                .postalCode(dto.postalCode())
                .country(dto.country())
                .type(AddressType.valueOf(dto.type()))
                .build();
    }
}

