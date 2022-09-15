package com.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.model.auth.LoginRequest;
import com.crm.model.auth.LoginResponse;
import com.crm.model.auth.User;
import com.crm.model.auth.UserDTO;
import com.crm.model.service.UserDetailsImpl;
import com.crm.repository.UserRepository;
import com.crm.service.UserService;
import com.crm.util.JwtUtils;
import com.crm.util.exception.CRMException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	//@RequestBody fa si che venga recuperato il contenuto dal body della richiesta (ovvero un oggetto JSON con le credenziali)
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		int numero=3;
		Authentication authentication = authenticationManager.authenticate( //Si genera il Token di autenticazione
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName().toLowerCase(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		LoginResponse loginResponse = new LoginResponse();//Si crea un oggetto LoginResponse e si impostano Token e Roles

		loginResponse.setToken(token);
		loginResponse.setRoles(roles);

		return ResponseEntity.ok(loginResponse);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO registerUser){
		try {
			return new ResponseEntity<User>(userService.addUser(registerUser).get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new CRMException("error while recording new user");
	}

}
