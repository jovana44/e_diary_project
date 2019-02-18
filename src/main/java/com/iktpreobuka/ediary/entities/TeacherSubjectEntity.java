package com.iktpreobuka.ediary.entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class TeacherSubjectEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="teacher")
	private TeacherEntity teacher;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="subject")
	private SubjectEntity subject;
	
	@OneToMany(mappedBy="teacherSubject", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<TeacherSubjectClassEntity> teachersSubjectsClasses;


	public TeacherSubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
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


	public TeacherEntity getTeacher() {
		return teacher;
	}


	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}


	public SubjectEntity getSubject() {
		return subject;
	}


	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}


	public List<TeacherSubjectClassEntity> getTeachersSubjectsClasses() {
		return teachersSubjectsClasses;
	}


	public void setTeachersSubjectsClasses(List<TeacherSubjectClassEntity> teachersSubjectsClasses) {
		this.teachersSubjectsClasses = teachersSubjectsClasses;
	}
	
	
	
}
