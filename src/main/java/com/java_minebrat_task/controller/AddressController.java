package com.java_minebrat_task.controller;

import com.java_minebrat_task.entity.Address;
import com.java_minebrat_task.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

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
