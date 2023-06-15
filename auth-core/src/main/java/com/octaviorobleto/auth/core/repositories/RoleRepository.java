package com.octaviorobleto.auth.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.octaviorobleto.auth.core.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByDescription(String description);

}
