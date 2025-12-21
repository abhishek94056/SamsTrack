package com.abhishek.SamsTrack.dto.attendance;

import java.util.List;

import lombok.Data;

@Data
public class StudentAttendanceGroupedDto {
    private Long studentId;
    private String studentName;
    private String email;
    private SubjectDto subject;
    private List<DateTimeDto> dateTime;
    private int totalClasses;
    private int presentCount;
    private double attendancePercentage;
}
