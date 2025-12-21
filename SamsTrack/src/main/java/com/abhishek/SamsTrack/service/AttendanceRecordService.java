package com.abhishek.SamsTrack.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhishek.SamsTrack.dao.AttendanceRecordDao;
import com.abhishek.SamsTrack.dto.attendance.DateTimeDto;
import com.abhishek.SamsTrack.dto.attendance.StudentAttendanceGroupedDto;
import com.abhishek.SamsTrack.dto.attendance.SubjectDto;
import com.abhishek.SamsTrack.entity.AttendanceRecord;
import com.abhishek.SamsTrack.entity.Student;
import com.abhishek.SamsTrack.entity.User;

@Service
public class AttendanceRecordService {

	@Autowired
	private AttendanceRecordDao dao;

	// ------------------ GET ALL ------------------
	public List<AttendanceRecord> getAllAttendanceRecords() {
		return dao.getAllAttendanceRecords();
	}

	// ------------------ GET BY FACULTY ------------------
	public List<AttendanceRecord> getAttendanceByFaculty(String facultyUsername) {
		return dao.getAttendanceByFaculty(facultyUsername);
	}

	// ------------------ GET BY DATE + SUBJECT ------------------
	public List<AttendanceRecord> getAllAttendanceRecords(String date, long subjectId) {
		return dao.getAllAttendanceRecords(date, subjectId);
	}

	// ------------------ GET STUDENT+SUBJECT GROUPED ------------------
	public StudentAttendanceGroupedDto getAttendanceForStudentAndSubject(long studentId, long subjectId) {

		List<AttendanceRecord> records = dao.getAttendanceForStudentAndSubject(studentId, subjectId);

		if (records == null || records.isEmpty()) {
			return null;
		}

		StudentAttendanceGroupedDto dto = new StudentAttendanceGroupedDto();

		// Initialize student + subject once
		for (AttendanceRecord record : records) {

			// Student details
			for (Student student : record.getStudents()) {
				if (student.getId().equals(studentId)) {
					dto.setStudentId(student.getId());
					dto.setStudentName(student.getName());
					dto.setEmail(student.getEmail());
					break;
				}
			}

			// Subject details
			SubjectDto subjectDto = new SubjectDto();
			subjectDto.setId(record.getSubject().getId());
			subjectDto.setName(record.getSubject().getName());
			dto.setSubject(subjectDto);

			break; // important
		}

		// Now fill timestamp list
		List<DateTimeDto> dateTimeList = new ArrayList<>();
		for (AttendanceRecord record : records) {

			DateTimeDto dtDto = new DateTimeDto();
			dtDto.setDate(record.getDate());
			dtDto.setTime(record.getTime());

			User faculty = record.getUser();
			if (faculty != null) {
				dtDto.setFacultyName(faculty.getFirstName());
			}

			dateTimeList.add(dtDto);
		}

		dto.setDateTime(dateTimeList);

		return dto;
	}

	// ------------------ SAVE ------------------
	public AttendanceRecord saveAttendance(AttendanceRecord attendanceRecord) {

		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()); // unique PK

		attendanceRecord.setId(id);

		return dao.saveAttendance(attendanceRecord);
	}

	// ------------------ GET BY Faculty + Subject + Date ------------------
	public List<AttendanceRecord> getAttendanceByFacultySubjectDate(String faculty, long subjectId, String date) {
		return dao.getAttendanceByFacultySubjectDate(faculty, subjectId, date);
	}
}