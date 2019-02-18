package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ActivityDto {

	@NotNull(message="Activity code must be provided.")
	@Size(min=2, max=10, message="Code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Activity name must be provided.")
	@Size(min=2, max=15, message="Activity name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonIgnore
	private List<GradingDto> gradingsDto;
	
	public ActivityDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ActivityDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	
	public ActivityDto(
			@NotNull(message = "Activity name must be provided.") @Size(min = 2, max = 2, message = "Activity name must be between {min} and {max} characters long.") String name) {
		super();
		this.name = name;
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
	
	
}
