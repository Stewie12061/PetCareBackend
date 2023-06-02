package com.example.food.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.food.model.ERole;
import com.example.food.model.LoginRequest;
import com.example.food.model.Role;
import com.example.food.model.SignUpRequest;
import com.example.food.model.User;
import com.example.food.model.UserDetail;
import com.example.food.payload.JwtResponse;
import com.example.food.payload.MessageResponse;
import com.example.food.repository.RoleRepository;
import com.example.food.repository.UserRepository;
import com.example.food.utils.JwtUtils;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetail userDetail = (UserDetail) authentication.getPrincipal();
		List<String> roles = userDetail.getAuthorities().stream()
						.map(item -> item.getAuthority())
						.collect(Collectors.toList());
		JwtResponse response = new JwtResponse(jwt, userDetail.getId(), userDetail.getUsername(), userDetail.getEmail(), userDetail.getFullname(), userDetail.getPhonenumber(), roles);

		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userRepository.existsByUsername(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: email is already use!"));
		}
		
		// Create new user's account
		User user = new User(
				signUpRequest.getUserName(),
				signUpRequest.getEmail(),
				signUpRequest.getPassword(),
				signUpRequest.getFullname(),
				signUpRequest.getPhonenumber()
		);
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});	
		}
		String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
		user.setPassword(encodedPassword);
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		
	}

	@PutMapping("/{userId}/updateUserInfo")
	public ResponseEntity<String> updateUser(
			@PathVariable Long userId, @RequestBody SignUpRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		user.setEmail(request.getEmail());
		user.setFullname(request.getFullname());
		user.setPhonenumber(request.getPhonenumber());

		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.OK).body("User information updated successfully");
	}

}
