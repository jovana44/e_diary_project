package com.iktpreobuka.ediary.entities.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeacherDto {

	@NotNull(message="Teacher code must be provided.")
	@Size(min=2, max=10, message="Teacher code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Teacher firstname must be provided.")
	@Size(min=2, max=15, message="Teacher firstname must be between {min} and {max} characters long.")
	private String firstName;
	
	@NotNull(message="Teacher lastname must be provided.")
	@Size(min=2, max=15, message="Teacher lastname must be between {min} and {max} characters long.")
	private String lastName;
	
	@NotNull(message="Subjects must be provided.")
	private List<SubjectDto> subjectsDto;
	
	@JsonIgnore
	private List<UserDto> usersDto;
 	
	public TeacherDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherDto(String code, String firstName, String lastName, List<SubjectDto> subjectsDto) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.subjectsDto = subjectsDto;
	}


	public List<SubjectDto> getSubjectsDto() {
		return subjectsDto;
	}


	public void setSubjectsDto(List<SubjectDto> subjectsDto) {
		this.subjectsDto = subjectsDto;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public TeacherDto(String code, String firstName, String lastName) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	
}
