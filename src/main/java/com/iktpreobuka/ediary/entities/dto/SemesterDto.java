package com.iktpreobuka.ediary.entities.dto;

import java.time.LocalDate;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SemesterDto {
	
	@NotNull(message="Semester code must be provided.")
	@Size(min=2, max=10, message="Code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Semester order number must be provided.")
	@Min(value=1, message="Semester order number must be 1 and higher.")
	@Max(value=2, message="Semester order number must be 2 and lower.")
	private Integer numberOfSem;
	
	//@Pattern(regexp="^(((0[1-9]{1}|1[0-9]|2[0-9])-(0[1-9]|1[0-2]))|(30.(04|06|09|11))|((30|31)-(01|03|05|07|08|10|12))).[0-9]{4}$", message="Format for date is not valid.")
	@NotNull(message="Date of semeater start must be provided.")
	@JsonFormat(pattern="dd-MM-yyyy", timezone="Europe/Belgrade")
	private LocalDate semesterStart;
	
	//@Pattern(regexp="^(((0[1-9]{1}|1[0-9]|2[0-9])-(0[1-9]|1[0-2]))|(30.(04|06|09|11))|((30|31)-(01|03|05|07|08|10|12))).[0-9]{4}$", message="Format for date is not valid.")
	//@Pattern(regexp="^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$", message="Format for date is not valid.")
	@NotNull(message="Date of semester end must be provided.")
	@JsonFormat(pattern="dd-MM-yyyy", timezone="Europe/Belgrade")
	private LocalDate semesterEnd;
	
	@NotNull(message="School year must be provided.")
	@Valid
	private SchoolYearDto schYearDto;
	
	@JsonIgnore
	private List<GradingDto> gradingsDto;
	
	public SemesterDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SemesterDto(String code, Integer numberOfSem, LocalDate semesterStart, LocalDate semesterEnd,
			SchoolYearDto schYearDto) {
		super();
		this.code = code;
		this.numberOfSem = numberOfSem;
		this.semesterStart = semesterStart;
		this.semesterEnd = semesterEnd;
		this.schYearDto = schYearDto;
	}
	
	public SemesterDto(
			@NotNull(message = "Semester order number must be provided.") @Min(value = 1, message = "Semester order number must be 1 and higher.") @Max(value = 2, message = "Semester order number must be 2 and lower.") Integer numberOfSem,
			LocalDate semesterStart, LocalDate semesterEnd) {
		super();
		this.numberOfSem = numberOfSem;
		this.semesterStart = semesterStart;
		this.semesterEnd = semesterEnd;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getNumberOfSem() {
		return numberOfSem;
	}
	public void setNumberOfSem(Integer numberOfSem) {
		this.numberOfSem = numberOfSem;
	}
	public LocalDate getSemesterStart() {
		return semesterStart;
	}
	public void setSemesterStart(LocalDate semesterStart) {
		this.semesterStart = semesterStart;
	}
	public LocalDate getSemesterEnd() {
		return semesterEnd;
	}
	public void setSemesterEnd(LocalDate semesterEnd) {
		this.semesterEnd = semesterEnd;
	}

	public SchoolYearDto getSchYearDto() {
		return schYearDto;
	}

	public void setSchYearDto(SchoolYearDto schYearDto) {
		this.schYearDto = schYearDto;
	}


	
	
	
	
}
