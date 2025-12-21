package com.abhishek.SamsTrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhishek.SamsTrack.dao.UserDao;
import com.abhishek.SamsTrack.dto.LoginRequest;
import com.abhishek.SamsTrack.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDao dao;

	public User registerUser(User user) {
		return dao.registerUser(user);
	}

	public User validateUser(LoginRequest request) {
		return dao.validateUser(request);
	}

	public User updateUser(User user) {
		return dao.updateUser(user);
	}

	public String deleteUserById(String username) {
		return dao.deleteUserById(username);
	}

	public List<User> getAllUser() {
		return dao.getAllUser();
	}

	public User getUserByName(String username) {
		return dao.getUserByName(username);
	}

	public List<User> getAllAdmins() {
		return dao.getAllAdmins();
	}

	public List<User> getAllFaculties() {
		return dao.getAllFaculties();
	}
}
