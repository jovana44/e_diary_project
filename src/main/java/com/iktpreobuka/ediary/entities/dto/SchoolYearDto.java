package com.iktpreobuka.ediary.entities.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SchoolYearDto {
	
	@NotNull(message="School year code must be provided.")
	@Size(min=2, max=10, message="Code must be between {min} and {max characters long}")
	private String code;
	
	@NotNull(message="School year name must be provided.")
	@Size(min=2, max=10, message="Year name must be between {min} and {max characters long}")
	private String name;
	
	@NotNull(message="Date of school year start must be provided.")
	@JsonFormat(pattern="dd-MM-yyyy", timezone="Europe/Belgrade")
	private LocalDate yearStart;
	
	@NotNull(message="Date of school year end must be provided.")
	@JsonFormat(pattern="dd-MM-yyyy", timezone="Europe/Belgrade")
	private LocalDate yearEnd;
	
	@JsonIgnore
	private List<SemesterDto> semestersDto;
	
	@JsonIgnore
	private List<ClassDto> classesDto;
	
	public SchoolYearDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SchoolYearDto(String code, String name, LocalDate yearStart, LocalDate yearEnd) {
		super();
		this.code = code;
		this.name = name;
		this.yearStart = yearStart;
		this.yearEnd = yearEnd;
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

	public LocalDate getYearStart() {
		return yearStart;
	}

	public void setYearStart(LocalDate yearStart) {
		this.yearStart = yearStart;
	}

	public LocalDate getYearEnd() {
		return yearEnd;
	}

	public void setYearEnd(LocalDate yearEnd) {
		this.yearEnd = yearEnd;
	}
	
	
}
