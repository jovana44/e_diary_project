package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClassDto {

	@NotNull(message="Code must be provided.")
	@Size(min=2, max=10, message="Code must be between {min} and {max} characters long.")
	private String code;
	
	@NotNull(message="Class year must be provided.")
	@Min(value=1, message="Class year must be 1 and higher.")
	@Max(value=8, message="Class year must be 8 and lower.")
	private Integer year;
	
	@NotNull(message="Name-number of class must be provided.")
	@Min(value=1, message="Name-number of class must be 1 and higher.")
	@Max(value=8, message="Name-number of class must be 8 and lower.")
	private Integer numberOfClass;
	
	@NotNull(message="School year of class must be provided.")
	private SchoolYearDto schYearDto;
	
	private List<TeacherSubjectDto> subjectsDto;
	
	public ClassDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassDto(String code, Integer year, Integer numberOfClass, SchoolYearDto schYearDto) {
		super();
		this.code = code;
		this.year = year;
		this.numberOfClass = numberOfClass;
		this.schYearDto = schYearDto;
	}
	

	public ClassDto(
			@NotNull(message = "Code must be provided.") @Size(min = 2, max = 10, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Class year must be provided.") @Min(value = 1, message = "Class year must be 1 and higher.") @Max(value = 8, message = "Class year must be 8 and lower.") Integer year,
			@NotNull(message = "Name-number of class must be provided.") @Min(value = 1, message = "Name-number of class must be 1 and higher.") @Max(value = 8, message = "Name-number of class must be 8 and lower.") Integer numberOfClass,
			@NotNull(message = "School year of class must be provided.") SchoolYearDto schYearDto,
			List<TeacherSubjectDto> subjectsDto) {
		super();
		this.code = code;
		this.year = year;
		this.numberOfClass = numberOfClass;
		this.schYearDto = schYearDto;
		this.subjectsDto = subjectsDto;
	}

	public List<TeacherSubjectDto> getSubjectsDto() {
		return subjectsDto;
	}

	public void setSubjectsDto(List<TeacherSubjectDto> subjectsDto) {
		this.subjectsDto = subjectsDto;
	}

	public ClassDto(
			@NotNull(message = "Class year must be provided.") @Min(value = 1, message = "Class year must be 1 and higher.") @Max(value = 8, message = "Class year must be 8 and lower.") Integer year,
			@NotNull(message = "Name-number of class must be provided.") @Min(value = 1, message = "Name-number of class must be 1 and higher.") @Max(value = 8, message = "Name-number of class must be 8 and lower.") Integer numberOfClass) {
		super();
		this.year = year;
		this.numberOfClass = numberOfClass;
	}

	public ClassDto(String code, Integer year, Integer numberOfClass) {
		super();
		this.code = code;
		this.year = year;
		this.numberOfClass = numberOfClass;
	}

	public SchoolYearDto getSchYearDto() {
		return schYearDto;
	}




	public void setSchYearDto(SchoolYearDto schYearDto) {
		this.schYearDto = schYearDto;
	}




	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getNumberOfClass() {
		return numberOfClass;
	}

	public void setNumberOfClass(Integer numberOfClass) {
		this.numberOfClass = numberOfClass;
	}

	
	
	
}
