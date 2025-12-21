package com.abhishek.SamsTrack.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhishek.SamsTrack.dto.AttendanceRecordRequest;
import com.abhishek.SamsTrack.dto.attendance.StudentAttendanceGroupedDto;
import com.abhishek.SamsTrack.entity.AttendanceRecord;
import com.abhishek.SamsTrack.entity.Student;
import com.abhishek.SamsTrack.entity.Subject;
import com.abhishek.SamsTrack.entity.User;
import com.abhishek.SamsTrack.service.AttendanceRecordService;
import com.abhishek.SamsTrack.service.StudentService;
import com.abhishek.SamsTrack.service.SubjectService;
import com.abhishek.SamsTrack.service.UserService;

@RestController
@RequestMapping("/attendance")
@CrossOrigin("http://localhost:4200")
public class AttendanceController {

	@Autowired
	private AttendanceRecordService attendanceRecordService;
	@Autowired
	private UserService userService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudentService studentService;

	@GetMapping("/all")
	public List<AttendanceRecord> getAllAttendanceRecords() {
		return attendanceRecordService.getAllAttendanceRecords();
	}

	@GetMapping("/faculty/{facultyUsername}")
	public List<AttendanceRecord> getAttendanceByFaculty(@PathVariable String facultyUsername) {
		return attendanceRecordService.getAttendanceByFaculty(facultyUsername);
	}

	@GetMapping("/date/{date}/subject/{subjectId}")
	public List<AttendanceRecord> getAttendanceByDateAndSubject(@PathVariable String date,
			@PathVariable long subjectId) {
		return attendanceRecordService.getAllAttendanceRecords(date, subjectId);
	}

	@GetMapping("/faculty/{faculty}/subject/{subjectId}/date/{date}")
	public List<AttendanceRecord> getAttendanceByFacultySubjectDate(@PathVariable String faculty,
			@PathVariable long subjectId, @PathVariable String date) {
		return attendanceRecordService.getAttendanceByFacultySubjectDate(faculty, subjectId, date);
	}

	@PostMapping("/take")
	public AttendanceRecord createAttendanceRecord(@RequestBody AttendanceRecordRequest request) {

		User user = userService.getUserByName(request.getUsername());
		Subject subject = subjectService.getSubjectById(request.getSubjectId());

		AttendanceRecord attendanceRecord = new AttendanceRecord();
		attendanceRecord.setUser(user);
		attendanceRecord.setSubject(subject);
		attendanceRecord.setDate(request.getDate());
		attendanceRecord.setTime(request.getTime());
		attendanceRecord.setStudents(request.getStudents());
		attendanceRecord.setNumberOfStudents(request.getStudents().size());
		Set<Student> fullStudents = new HashSet<>();
		for (Student s : request.getStudents()) {
			Student st = studentService.getStudentById(s.getId());
			fullStudents.add(st);
		}
		attendanceRecord.setStudents(fullStudents);

		attendanceRecord.setNumberOfStudents(fullStudents.size());

		return attendanceRecordService.saveAttendance(attendanceRecord);
	}

	@GetMapping("/student/{studentId}/subject/{subjectId}")
	public StudentAttendanceGroupedDto getAttendanceForStudentAndSubject(@PathVariable long studentId,
			@PathVariable long subjectId) {
		return attendanceRecordService.getAttendanceForStudentAndSubject(studentId, subjectId);
	}
}