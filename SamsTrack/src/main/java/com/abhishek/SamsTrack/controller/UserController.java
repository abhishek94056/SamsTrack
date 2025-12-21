package com.abhishek.SamsTrack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.SamsTrack.config.JwtUtil;
import com.abhishek.SamsTrack.dto.LoginRequest;
import com.abhishek.SamsTrack.entity.User;
import com.abhishek.SamsTrack.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return service.registerUser(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		User user = service.validateUser(request); // validate only

		String token = jwtUtil.generateToken(user.getUsername());

		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("username", user.getUsername());
		response.put("role", user.getRole());

		return ResponseEntity.ok(response);
	}

	@PutMapping("/update")
	public User updateUser(@RequestBody User user) {
		return service.updateUser(user);
	}

	@DeleteMapping("/{username}")
	public String deleteUserById(@PathVariable String username) {
		return service.deleteUserById(username);
	}

	@GetMapping("/all")
	public List<User> getAllUser() {
		return service.getAllUser();
	}

	@GetMapping("/{username}")
	public User getUserByName(@PathVariable String username) {
		return service.getUserByName(username);
	}

	@GetMapping("/admin/all")
	public List<User> getAllAdmins() {
		return service.getAllAdmins();
	}

	@GetMapping("/faculty/all")
	public List<User> getAllFaculties() {
		return service.getAllFaculties();
	}

}
