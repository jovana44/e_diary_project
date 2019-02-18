package com.iktpreobuka.ediary.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class StudentClassEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="studentt")
	private StudentEntity studentt;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="classs")
	private ClassEntity classs;

	public StudentClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentClassEntity(StudentEntity studentt, ClassEntity classs) {
		super();
		this.studentt = studentt;
		this.classs = classs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public StudentEntity getStudentt() {
		return studentt;
	}

	public void setStudentt(StudentEntity studentt) {
		this.studentt = studentt;
	}

	public ClassEntity getClasss() {
		return classs;
	}

	public void setClasss(ClassEntity classs) {
		this.classs = classs;
	}
	
	
	
}
