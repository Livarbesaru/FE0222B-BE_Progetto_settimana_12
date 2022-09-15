package com.crm.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.model.auth.Role;
import com.crm.model.auth.Roles;
import com.crm.model.auth.User;
import com.crm.model.auth.UserDTO;
import com.crm.repository.RoleRepository;
import com.crm.repository.UserRepository;
import com.crm.util.exception.CRMException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public Page<User> getAll(Pageable pageable){
		return userRepository.findAll(pageable);
	}

	
	public Optional<User> addUser(UserDTO newUserDTO) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		
		if(userRepository.findByUserName(newUserDTO.getUserName().toLowerCase()).isPresent() ||
			userRepository.findByEmail(newUserDTO.getEmail()).isPresent()) {
			throw new CRMException("username o email gia esistenti");
		}

		Set<Role> roles = new HashSet<>();
		User newUser=new User();
		//ci controlliamo se sono stati specificati dei ruoli all'utente
		if(newUserDTO.getRoles()!=null && newUserDTO.getRoles().size()>0) {
			//se specificati si li cicla
			for(String r:newUserDTO.getRoles()) {
				//se il ruolo attuale e' user
				if(r.toLowerCase().equals("user")) {
					//controlla se il ruolo user e' gia presente in database
					if(roleRepository.findByRoleName(Roles.ROLE_USER).isPresent()) {
						roles.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
					}
					else {
						//se non presente lo aggiunge
						roles.add(new Role(Roles.ROLE_USER));
					}
				}
				//se il ruolo attuale e' admin
				else if(r.toLowerCase().equals("admin")) {
					//controlla se il ruolo admin e' gia presente in database
					if(roleRepository.findByRoleName(Roles.ROLE_ADMIN).isPresent()) {
						roles.add(roleRepository.findByRoleName(Roles.ROLE_ADMIN).get());
					}
					else {
						//se non presente lo aggiunge
						roles.add(new Role(Roles.ROLE_ADMIN));
					}
				}
			}
		}
		if(newUserDTO.getRoles()!=null && roles.size()>0) {
		}else {
			if(roleRepository.findByRoleName(Roles.ROLE_USER).isPresent()) {
				roles.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
			}else {
				roles.add(new Role(Roles.ROLE_USER));
			}
		}
		newUser.setUserName(newUserDTO.getUserName().toLowerCase());
		newUser.setPassword(bCrypt.encode(newUserDTO.getPassword()));
		newUser.setEmail(newUserDTO.getEmail());
		newUser.setRoles(roles);
		newUser.setActive(true);

		//roleRepository.save(role);
		userRepository.save(newUser);
		return Optional.of(newUser);
	}

}
