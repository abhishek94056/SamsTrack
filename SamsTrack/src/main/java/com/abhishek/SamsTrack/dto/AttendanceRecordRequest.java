package com.abhishek.SamsTrack.dto;

import java.util.Set;

import com.abhishek.SamsTrack.entity.Student;

import lombok.Data;

@Data
public class AttendanceRecordRequest {
	
	private String username;
	private Long subjectId;
	private String date;
	private String time;
    private int numberOfStudents;
	private Set<Student> students;
}

