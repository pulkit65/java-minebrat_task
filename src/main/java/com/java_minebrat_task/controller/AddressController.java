package com.java_minebrat_task.controller;

import com.java_minebrat_task.entity.Address;
import com.java_minebrat_task.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    /**
     * Get all addresses with optional filters
     */
    @GetMapping
    public ResponseEntity<Page<Address>> getAllAddresses(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "city,asc") String[] sort) {

        Sort sortOrder = Sort.by(Sort.Order.by(sort[0])
                .with(sort.length > 1 && sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC));
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Address> addresses = addressService.getAllAddresses(city, state, country, pageable);
        return ResponseEntity.ok(addresses);
    }

    /**
     * Get address by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable UUID id) {
        Address address = addressService.getAddress(id);
        return ResponseEntity.ok(address);
    }

    /**
     * Update address by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable UUID id, @RequestBody Address address) {
        Address updated = addressService.updateAddress(id, address);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete address by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
