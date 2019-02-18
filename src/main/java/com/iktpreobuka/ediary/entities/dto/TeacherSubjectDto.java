package com.iktpreobuka.ediary.entities.dto;

import javax.validation.constraints.NotNull;

public class TeacherSubjectDto {

	@NotNull(message="Teacher must be provided.")
	private TeacherDto teacherDto;
	
	@NotNull(message="Subject must be provided.")
	private SubjectDto subjectDto;
	
	@NotNull(message="Weekly hours must be provided.")
	private Integer weeklyHours;
	
	public TeacherSubjectDto(TeacherDto teacherDto, SubjectDto subjectDto, Integer weeklyHours) {
		super();
		this.teacherDto = teacherDto;
		this.subjectDto = subjectDto;
		this.weeklyHours = weeklyHours;
	}

	public TeacherSubjectDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherDto getTeacherDto() {
		return teacherDto;
	}

	public void setTeacherDto(TeacherDto teacherDto) {
		this.teacherDto = teacherDto;
	}

	public SubjectDto getSubjectDto() {
		return subjectDto;
	}

	public void setSubjectDto(SubjectDto subjectDto) {
		this.subjectDto = subjectDto;
	}

	public Integer getWeeklyHours() {
		return weeklyHours;
	}

	public void setWeeklyHours(Integer weeklyHours) {
		this.weeklyHours = weeklyHours;
	}
	
	
	
	
}
