package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

public class StudentMarksDto {

	private StudentDto studentDto;
	private List<MarkDto> marksFirstSem;
	private List<MarkDto> marksSecondSem;
	
	public StudentMarksDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentMarksDto(StudentDto studentDto, List<MarkDto> marksFirstSem, List<MarkDto> marksSecondSem) {
		super();
		this.studentDto = studentDto;
		this.marksFirstSem = marksFirstSem;
		this.marksSecondSem = marksSecondSem;
	}

	public StudentDto getStudentDto() {
		return studentDto;
	}

	public void setStudentDto(StudentDto studentDto) {
		this.studentDto = studentDto;
	}



	public List<MarkDto> getMarksFirstSem() {
		return marksFirstSem;
	}



	public void setMarksFirstSem(List<MarkDto> marksFirstSem) {
		this.marksFirstSem = marksFirstSem;
	}



	public List<MarkDto> getMarksSecondSem() {
		return marksSecondSem;
	}



	public void setMarksSecondSem(List<MarkDto> marksSecondSem) {
		this.marksSecondSem = marksSecondSem;
	}

	
	
	
}
