package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class URoleDto {

	@NotNull(message="User role code must be provided.")
	@Size(min=2, max=10, message="User role code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="User role name must be provided.")
	@Size(min=2, max=15, message="User role name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonIgnore
	private List<UserDto> users;
	
	public URoleDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}


	public URoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	
	
	
}

