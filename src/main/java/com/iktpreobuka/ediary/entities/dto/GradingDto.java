package com.iktpreobuka.ediary.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GradingDto {

	@NotNull(message="Teacher must be provided.")
	private TeacherDto teacherDto;
	
	@NotNull(message="Student must be provided.")
	private StudentDto studentDto;
	
	@NotNull(message="Subject must be provided.")
	private SubjectDto subjectDto;
	
	@NotNull(message="Activity must be provided.")
	private ActivityDto activityDto;
	
	private SemesterDto semesterDto;

	@JsonFormat(pattern="dd-MM-yyyy", timezone="Europe/Belgrade")
	private LocalDate date;
	
	@NotNull(message="Mark must be provided.")
	@Min(value=1, message="Mark must be 1 and higher.")
	@Max(value=5, message="Mark must be 5 and lower.")
    private Integer mark;
	
    
	public GradingDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradingDto(TeacherDto teacherDto, StudentDto studentDto, SubjectDto subjectDto, ActivityDto activityDto,
			SemesterDto semesterDto, LocalDate date,
			@NotNull(message = "Mark must be provided.") @Min(value = 1, message = "Mark must be 1 and higher.") @Max(value = 5, message = "Mark must be 5 and lower.") Integer mark) {
		super();
		this.teacherDto = teacherDto;
		this.studentDto = studentDto;
		this.subjectDto = subjectDto;
		this.activityDto = activityDto;
		this.semesterDto = semesterDto;
		this.date = date;
		this.mark = mark;
	}


	public TeacherDto getTeacherDto() {
		return teacherDto;
	}

	public void setTeacherDto(TeacherDto teacherDto) {
		this.teacherDto = teacherDto;
	}

	public StudentDto getStudentDto() {
		return studentDto;
	}

	public void setStudentDto(StudentDto studentDto) {
		this.studentDto = studentDto;
	}

	public SubjectDto getSubjectDto() {
		return subjectDto;
	}

	public void setSubjectDto(SubjectDto subjectDto) {
		this.subjectDto = subjectDto;
	}

	public ActivityDto getActivityDto() {
		return activityDto;
	}

	public void setActivityDto(ActivityDto activityDto) {
		this.activityDto = activityDto;
	}

	public SemesterDto getSemesterDto() {
		return semesterDto;
	}

	public void setSemesterDto(SemesterDto semesterDto) {
		this.semesterDto = semesterDto;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}


	
	
}
