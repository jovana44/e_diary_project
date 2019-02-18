package com.iktpreobuka.ediary.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectDto {

	@NotNull(message="Subject code must be provided.")
	@Size(min=2, max=10, message="Subject code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Subject name must be provided.")
	@Size(min=2, max=15, message="Subject name must be between {min} and {max} characters long.")
	private String name;
	
	public SubjectDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public SubjectDto(
			@NotNull(message = "Subject name must be provided.") @Size(min = 2, max = 15, message = "Subject name must be between {min} and {max} characters long.") String name) {
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


	public SubjectDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
