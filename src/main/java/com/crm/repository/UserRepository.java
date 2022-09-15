package com.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.model.auth.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findById(Long id);

	public Optional<User> findByUserName(String userName);
	
	public Optional<User> findByEmail(String email);

	public boolean existsByEmail(String email);

	public boolean existsByUserName(String userName);
}
