package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TeacherSubjectClassDto {

	@NotNull(message="Class must be provided.")
	private ClassDto classDto;
	
	@NotNull(message="List of teachers and subjects must be provided.")
	@Valid
	private List<TeacherSubjectDto> teacherSubjectsDto;
	
	public TeacherSubjectClassDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassDto getClassDto() {
		return classDto;
	}

	public void setClassDto(ClassDto classDto) {
		this.classDto = classDto;
	}

	public List<TeacherSubjectDto> getTeacherSubjectsDto() {
		return teacherSubjectsDto;
	}

	public void setTeacherSubjectsDto(List<TeacherSubjectDto> teacherSubjectsDto) {
		this.teacherSubjectsDto = teacherSubjectsDto;
	}

	public TeacherSubjectClassDto(ClassDto classDto, List<TeacherSubjectDto> teacherSubjectsDto) {
		super();
		this.classDto = classDto;
		this.teacherSubjectsDto = teacherSubjectsDto;
	}

	
	

	
}
