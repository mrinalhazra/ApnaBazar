package com.app.apnabazar.repository;

import com.app.apnabazar.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
