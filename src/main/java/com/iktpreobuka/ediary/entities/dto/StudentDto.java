package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StudentDto {

	@NotNull(message="Student code must be provided.")
	@Size(min=2, max=10, message="Student code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Student firstname must be provided.")
	@Size(min=2, max=15, message="Student firstname must be between {min} and {max} characters long.")
	private String firstName;
	
	@NotNull(message="Student lastname must be provided.")
	@Size(min=2, max=10, message="Student lastname must be between {min} and {max} characters long.")
	private String lastName;
	
	@NotNull(message="Parent must be provided.")
	@Valid
	private ParentDto parentDto;
	
	@NotNull(message="Class must be provided.")
	private ClassDto classDto;
	
	@JsonIgnore
	private List<GradingDto> gradingsDto;
	
	@JsonIgnore
	private List<UserDto> usersDto;
	
	public StudentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentDto(
			@NotNull(message = "Code must be provided.") @Size(min = 2, max = 10, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Firstname must be provided.") @Size(min = 2, max = 15, message = "Firstname must be between {min} and {max} characters long.") String firstName,
			@NotNull(message = "Lastname must be provided.") @Size(min = 2, max = 10, message = "Lastname must be between {min} and {max} characters long.") String lastName,
			ClassDto classDto) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.classDto = classDto;
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

	public ParentDto getParentDto() {
		return parentDto;
	}

	public void setParentDto(ParentDto parentDto) {
		this.parentDto = parentDto;
	}

	public ClassDto getClassDto() {
		return classDto;
	}

	public void setClassDto(ClassDto classDto) {
		this.classDto = classDto;
	}

	public StudentDto(String code, String firstName, String lastName) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public StudentDto(String code, String firstName, String lastName, ParentDto parentDto, ClassDto classDto) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.parentDto = parentDto;
		this.classDto = classDto;
	}

	
	
	
}
