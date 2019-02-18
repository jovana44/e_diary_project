package com.iktpreobuka.ediary.entities.dto;

import java.util.List;

public class SubjectMarksDto {

	private SubjectDto subjectDto;
	private List<MarkDto> marksFirstSem;
	private List<MarkDto> marksSecondSem;
	
	public SubjectMarksDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectMarksDto(SubjectDto subjectDto, List<MarkDto> marksFirstSem, List<MarkDto> marksSecondSem) {
		super();
		this.subjectDto = subjectDto;
		this.marksFirstSem = marksFirstSem;
		this.marksSecondSem = marksSecondSem;
	}

	public SubjectDto getSubjectDto() {
		return subjectDto;
	}

	public void setSubjectDto(SubjectDto subjectDto) {
		this.subjectDto = subjectDto;
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
