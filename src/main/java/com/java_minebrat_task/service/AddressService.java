package com.java_minebrat_task.service;

import com.java_minebrat_task.entity.Address;
import com.java_minebrat_task.repository.AddressRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public Address getAddress(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with ID " + id));
    }

    @Transactional(readOnly = true)
    public Page<Address> getAllAddresses(String city, String state, String country, Pageable pageable) {
        // Use Specification-like filtering via a simple approach
        return addressRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (city != null && !city.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%"));
            if (state != null && !state.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("state")), "%" + state.toLowerCase() + "%"));
            if (country != null && !country.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("country")), "%" + country.toLowerCase() + "%"));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
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
