package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PersonDto {

	@NotNull(message="Person code must be provided.")
	@Size(min=2, max=10, message="Code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Person firstname must be provided.")
	@Size(min=2, max=15, message="Firstname must be between {min} and {max} characters long.")
	private String firstName;
	
	@NotNull(message="Person lastname must be provided.")
	@Size(min=2, max=15, message="Lastname must be between {min} and {max} characters long.")
	private String lastName;
	
	@JsonIgnore
	private List<UserDto> usersDto;

	public PersonDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonDto(
			@NotNull(message = "Code must be provided.") @Size(min = 2, max = 10, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Firstname must be provided.") @Size(min = 2, max = 15, message = "Firstname must be between {min} and {max} characters long.") String firstName,
			@NotNull(message = "Lastname must be provided.") @Size(min = 2, max = 15, message = "Lastname must be between {min} and {max} characters long.") String lastName) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
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
	
	
}
