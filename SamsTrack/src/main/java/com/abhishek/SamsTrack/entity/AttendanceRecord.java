package com.abhishek.SamsTrack.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AttendanceRecord {
    
	@Id
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "faculty")
    private User user;
    
    private int numberOfStudents;
    
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String date;
    private String time;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "attendance_students",
        joinColumns = @JoinColumn(name = "attendance_record_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;
}
