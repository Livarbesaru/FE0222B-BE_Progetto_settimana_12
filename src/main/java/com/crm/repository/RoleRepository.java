package com.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.model.auth.Role;
import com.crm.model.auth.Roles;

public interface RoleRepository extends JpaRepository<Role, Integer> {
//Cerca in base al Role name e spring crea le QUERY
	Optional<Role> findByRoleName(Roles role);
}
