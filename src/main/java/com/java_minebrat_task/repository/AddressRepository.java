package com.java_minebrat_task.repository;


import com.java_minebrat_task.entity.Address;
import com.java_minebrat_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByUser(User user);
}

