package com.java_minebrat_task.service;

import com.java_minebrat_task.entity.Address;
import com.java_minebrat_task.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public Address getAddress(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID " + id));
    }

    @Transactional
    public Address updateAddress(UUID id, Address updated) {
        Address existing = getAddress(id);
        existing.setStreet(updated.getStreet());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setCountry(updated.getCountry());
        existing.setPostalCode(updated.getPostalCode());
        Address saved = addressRepository.save(existing);
        auditService.record("ADDRESS_UPDATED", "Updated address " + id);
        return saved;
    }

    @Transactional
    public void deleteAddress(UUID id) {
        Address address = getAddress(id);
        addressRepository.delete(address);
        auditService.record("ADDRESS_DELETED", "Deleted address " + id);
    }
}

