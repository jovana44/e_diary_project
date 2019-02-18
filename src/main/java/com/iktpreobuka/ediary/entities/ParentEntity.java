package com.iktpreobuka.ediary.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentEntity extends PersonEntity{

	@Column(unique=true)
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<StudentEntity> students;

	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ParentEntity(String code, String firstName, String lastName,String email ) {
		  super( code, firstName, lastName);
		  this.email=email;
			// TODO Auto-generated constructor stub
		}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ParentEntity(String email) {
		super();
		this.email = email;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	
	
	
	
	
}
