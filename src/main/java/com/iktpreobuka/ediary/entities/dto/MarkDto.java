package com.iktpreobuka.ediary.entities.dto;

public class MarkDto {

	private Integer mark;
	private String activityName;
	
	public MarkDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MarkDto(Integer mark, String activityName) {
		super();
		this.mark = mark;
		this.activityName = activityName;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	
}
