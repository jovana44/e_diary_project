package com.iktpreobuka.ediary.entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;


@Entity
public class SubjectEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	@Column(unique=true)
	private String code;
	@Column(unique=true)
	private String name;
	
	@OneToMany(mappedBy="subject", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<TeacherSubjectEntity> teachersSubjects;
	
	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SubjectEntity(String code, String name) {
		super();
		this.code = code;
		this.name = name;
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

	

	public List<TeacherSubjectEntity> getTeachersSubjects() {
		return teachersSubjects;
	}

	public void setTeachersSubjects(List<TeacherSubjectEntity> teachersSubjects) {
		this.teachersSubjects = teachersSubjects;
	}
	
	
	
	
	
	
}
