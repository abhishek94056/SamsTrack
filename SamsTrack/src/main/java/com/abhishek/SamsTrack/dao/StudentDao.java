package com.abhishek.SamsTrack.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abhishek.SamsTrack.entity.Student;
import com.abhishek.SamsTrack.exception.EntityNotFoundException;

@Repository
public class StudentDao {

	@Autowired
	private SessionFactory sf;

	// ---------------- GET MULTIPLE STUDENTS BY IDs ------------------
	public List<Student> getAllStudentsById(List<Long> studentIds) {
		try (Session ss = sf.openSession()) {
			return ss.createQuery("from Student s where s.id in (:ids)", Student.class).setParameter("ids", studentIds)
					.list();
		}
	}

	// ---------------- GET ALL STUDENTS ------------------
	public List<Student> getAllStudents() {
		try (Session ss = sf.openSession()) {
			return ss.createQuery("from Student", Student.class).list();
		}
	}

	// ---------------- CREATE STUDENT ------------------
	public Student createStudent(Student s) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {
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

	// ---------------- GET STUDENT BY ID ------------------
	public Student getStudentById(long id) {
		try (Session ss = sf.openSession()) {
			Student s = ss.find(Student.class, id);
			if (s == null) {
				throw new EntityNotFoundException("Student not found");
			}
			return s;
		}
	}

	// ---------------- UPDATE STUDENT ------------------
	public Student updateStudent(Student s) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {
			tr = ss.beginTransaction();
			ss.merge(s);
			tr.commit();
			return s;

		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}

	// ---------------- DELETE STUDENT ------------------
	public String deleteStudent(long id) {
		Transaction tr = null;
		try (Session ss = sf.openSession()) {

			Student s = ss.find(Student.class, id);
			if (s == null) {
				throw new EntityNotFoundException("Student not found");
			}

			tr = ss.beginTransaction();
			ss.remove(s);
			tr.commit();
			return "Student deleted successfully";

		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}
}
