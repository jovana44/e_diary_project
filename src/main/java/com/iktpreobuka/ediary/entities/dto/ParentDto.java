package com.iktpreobuka.ediary.entities.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ParentDto {

	@NotNull(message="Parent code must be provided.")
	@Size(min=2, max=10, message="Code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Parent firstname must be provided.")
	@Size(min=2, max=15, message="Firstname must be between {min} and {max} characters long.")
	private String firstName;
	
	@NotNull(message="Parent lastname must be provided.")
	@Size(min=2, max=15, message="Lastname must be between {min} and {max} characters long.")
	private String lastName;
	
	@NotNull(message="Parent email must be provided.")
	//@Email(message="Email is not valid.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"	+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Email is not valid.")
	private String email;
	
	@JsonIgnore
	private List<UserDto> usersDto;
	
	public ParentDto() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ParentDto(String code, String firstName, String lastName, String email) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	
}
