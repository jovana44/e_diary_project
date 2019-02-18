package com.iktpreobuka.ediary.entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class TeacherEntity extends PersonEntity{
	
	@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<TeacherSubjectEntity> teachersSubjects;

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity(String code, String firstName, String lastName) {
		super(code, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

	public List<TeacherSubjectEntity> getTeachersSubjects() {
		return teachersSubjects;
	}

	public void setTeachersSubjects(List<TeacherSubjectEntity> teachersSubjects) {
		this.teachersSubjects = teachersSubjects;
	}
	
	

	
}
