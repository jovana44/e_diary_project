package com.iktpreobuka.ediary.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto{
	
	@NotNull(message="Username must be provided.")
	@Size(min=2, max=10, message="Username must be between {min} and {max} characters long.")
	private String username;
	
	@JsonIgnore
	@NotNull(message="Password must be provided.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,}$", message="Password is not valid.")
    private String password;
	
	@NotNull(message="Person must be provided.")
	private PersonDto personDto;
	
	private URoleDto uRoleDto;
	
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

		public UserDto(
			@NotNull(message = "Username must be provided.") @Size(min = 2, max = 10, message = "Username must be between {min} and {max} characters long.") String username,
			@NotNull(message = "Password must be provided.") String password,
			@NotNull(message = "Person must be provided.") PersonDto personDto, URoleDto uRoleDto) {
		super();
		this.username = username;
		this.password = password;
		this.personDto = personDto;
		this.uRoleDto = uRoleDto;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PersonDto getPersonDto() {
		return personDto;
	}

	public void setPersonDto(PersonDto personDto) {
		this.personDto = personDto;
	}

	public URoleDto getuRoleDto() {
		return uRoleDto;
	}

	public void setuRoleDto(URoleDto uRoleDto) {
		this.uRoleDto = uRoleDto;
	}


	

	
}
