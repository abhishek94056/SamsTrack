package com.abhishek.SamsTrack.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abhishek.SamsTrack.entity.AttendanceRecord;

@Repository
public class AttendanceRecordDao {

    @Autowired
    private SessionFactory factory;

    // ---------------- GET ALL ATTENDANCE ----------------
    public List<AttendanceRecord> getAllAttendanceRecords() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "from AttendanceRecord",
                    AttendanceRecord.class
            ).list();
        }
    }

    // ---------------- GET BY FACULTY + SUBJECT + DATE ----------------
    public List<AttendanceRecord> getAttendanceByFacultySubjectDate(
            String facultyUsername, long subjectId, String date) {

        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "from AttendanceRecord ar " +
                    "where ar.user.username = :faculty " +
                    "and ar.subject.id = :subjectId " +
                    "and ar.date = :date",
                    AttendanceRecord.class
            )
            .setParameter("faculty", facultyUsername)
            .setParameter("subjectId", subjectId)
            .setParameter("date", date)
            .list();
        }
    }

    // ---------------- GET BY FACULTY ----------------
    public List<AttendanceRecord> getAttendanceByFaculty(String facultyUsername) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "from AttendanceRecord ar where ar.user.username = :faculty",
                    AttendanceRecord.class
            )
            .setParameter("faculty", facultyUsername)
            .list();
        }
    }

    // ---------------- GET BY DATE + SUBJECT ----------------
    public List<AttendanceRecord> getAllAttendanceRecords(
            String date, long subjectId) {

        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "from AttendanceRecord ar " +
                    "where ar.date = :date and ar.subject.id = :subjectId",
                    AttendanceRecord.class
            )
            .setParameter("date", date)
            .setParameter("subjectId", subjectId)
            .list();
        }
    }

    // ---------------- GET STUDENT + SUBJECT ATTENDANCE ----------------
    public List<AttendanceRecord> getAttendanceForStudentAndSubject(
            Long studentId, Long subjectId) {

        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "select distinct ar from AttendanceRecord ar " +
                    "join ar.students s " +
                    "where s.id = :studentId " +
                    "and ar.subject.id = :subjectId " +
                    "order by ar.date asc",
                    AttendanceRecord.class
            )
            .setParameter("studentId", studentId)
            .setParameter("subjectId", subjectId)
            .list();
        }
    }

    // ---------------- SAVE ATTENDANCE ----------------
    public AttendanceRecord saveAttendance(AttendanceRecord attendanceRecord) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(attendanceRecord);
            tx.commit();
            return attendanceRecord;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to save attendance record", e);
        }
    }
}
