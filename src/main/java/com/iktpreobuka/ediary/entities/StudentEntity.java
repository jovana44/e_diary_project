package com.iktpreobuka.ediary.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class StudentEntity extends PersonEntity{
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="parent")
	private ParentEntity parent;
	
	@OneToMany(mappedBy="studentt", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<StudentClassEntity> studentClasses;
	
	@OneToMany(mappedBy="student", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<GradingEntity> gradings;

	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentEntity( String code, String firstName, String lastName) {
		super( code, firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
	public StudentEntity( String code, String firstName, String lastName, ParentEntity parent) {
		super( code, firstName, lastName);
		this.parent=parent;
		// TODO Auto-generated constructor stub
	}


	public List<StudentClassEntity> getStudentClasses() {
		return studentClasses;
	}

	public void setStudentClasses(List<StudentClassEntity> studentClasses) {
		this.studentClasses = studentClasses;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}


	public List<GradingEntity> getGradings() {
		return gradings;
	}

	public void setGradings(List<GradingEntity> gradings) {
		this.gradings = gradings;
	}
	
	

	
}
