package com.abhishek.SamsTrack.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.abhishek.SamsTrack.dto.LoginRequest;
import com.abhishek.SamsTrack.entity.User;
import com.abhishek.SamsTrack.exception.DuplicateResourceException;
import com.abhishek.SamsTrack.exception.EntityNotFoundException;
import com.abhishek.SamsTrack.exception.InvalidCredentialsException;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sf;

	@Autowired
	private BCryptPasswordEncoder encoder;

	// ---------------- REGISTER USER ------------------
	public User registerUser(User user) {
		Transaction tx = null;

		try (Session session = sf.openSession()) {

			User existing = session.find(User.class, user.getUsername());
			if (existing != null) {
				throw new DuplicateResourceException("User already exists");
			}

			// Encrypt password
			user.setPassword(encoder.encode(user.getPassword()));

			tx = session.beginTransaction();
			session.persist(user);
			tx.commit();

			return user;

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
	}

	// ---------------- LOGIN ------------------
	public User validateUser(LoginRequest request) {

		try (Session ss = sf.openSession()) {

			User u = ss.find(User.class, request.getUsername());

			if (u == null) {
				throw new EntityNotFoundException("User not found");
			}

			if (!encoder.matches(request.getPassword(), u.getPassword())) {
				throw new InvalidCredentialsException("Invalid password");
			}

			return u;
		}
	}

	// ---------------- UPDATE USER ------------------
	public User updateUser(User u) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {
			tr = ss.beginTransaction();
			ss.merge(u);
			tr.commit();
			return u;

		} catch (RuntimeException e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}

	// ---------------- DELETE USER ------------------
	public String deleteUserById(String username) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {
			User u = ss.find(User.class, username);

			if (u == null) {
				throw new EntityNotFoundException("User not found");
			}

			tr = ss.beginTransaction();
			ss.remove(u);
			tr.commit();
			return "User deleted successfully";

		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}

	// ---------------- GET ALL USERS ------------------
	public List<User> getAllUser() {
		try (Session ss = sf.openSession()) {
			return ss.createQuery("from User", User.class).list();
		}
	}

	// ---------------- GET USER BY NAME ------------------
	public User getUserByName(String username) {
		try (Session ss = sf.openSession()) {
			User u = ss.find(User.class, username);
			if (u == null) {
				throw new EntityNotFoundException("User not found");
			}
			return u;
		}
	}

	// ---------------- GET ADMINS ------------------
	public List<User> getAllAdmins() {
		try (Session ss = sf.openSession()) {
			return ss.createQuery("from User where role = :role", User.class).setParameter("role", "admin").list();
		}
	}

	// ---------------- GET FACULTIES ------------------
	public List<User> getAllFaculties() {
		try (Session ss = sf.openSession()) {
			return ss.createQuery("from User where role = :role", User.class).setParameter("role", "faculty").list();
		}
	}
}
