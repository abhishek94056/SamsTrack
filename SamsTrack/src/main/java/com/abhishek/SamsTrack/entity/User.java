package com.abhishek.SamsTrack.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
}
