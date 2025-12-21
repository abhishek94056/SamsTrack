package com.abhishek.SamsTrack.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abhishek.SamsTrack.entity.Subject;
import com.abhishek.SamsTrack.exception.DuplicateResourceException;
import com.abhishek.SamsTrack.exception.EntityNotFoundException;

@Repository
public class SubjectDao {

	@Autowired
	private SessionFactory sf;

	// ------------------- GET SUBJECT BY ID -------------------
	public Subject getSubjectById(long subjectId) {
		try (Session ss = sf.openSession()) {
			Subject s = ss.find(Subject.class, subjectId);
			if (s == null) {
				throw new EntityNotFoundException("Subject not found");
			}
			return s;
		}
	}

	// ------------------- GET ALL SUBJECTS -------------------
	public List<Subject> getAllSubjects() {
		try (Session ss = sf.openSession()) {
			return ss.createQuery("from Subject", Subject.class).list();
		}
	}

	// ------------------- CREATE SUBJECT -------------------
	public Subject createSubject(Subject s) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {

			// check duplicate subject name
			List<Subject> list = ss.createQuery("from Subject where name = :name", Subject.class)
					.setParameter("name", s.getName()).list();

			if (!list.isEmpty()) {
				throw new DuplicateResourceException("Subject already exists");
			}

			tr = ss.beginTransaction();
			ss.persist(s);
			tr.commit();
			return s;

		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}

	// ------------------- UPDATE SUBJECT -------------------
	public Subject updateSubject(Subject s) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {
			tr = ss.beginTransaction();
			ss.merge(s);
			tr.commit();
			return s;

		} catch (RuntimeException e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}

	// ------------------- DELETE SUBJECT -------------------
	public String deleteSubject(long id) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {

			Subject s = ss.find(Subject.class, id);
			if (s == null) {
				throw new RuntimeException("Subject not found");
			}

			tr = ss.beginTransaction();
			ss.remove(s);
			tr.commit();
			return "Subject deleted successfully";

		} catch (RuntimeException e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}
}
