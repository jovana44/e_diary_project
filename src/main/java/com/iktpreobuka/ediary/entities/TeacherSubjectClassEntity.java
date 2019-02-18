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
public class TeacherSubjectClassEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	private Integer weeklyHours;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="teacherSubject")
	private TeacherSubjectEntity teacherSubject;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="classss")
	private ClassEntity classss;	
	
	@OneToMany(mappedBy="teacherSubjectClass", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<GradingEntity> gradings;

	
	public TeacherSubjectClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getWeeklyHours() {
		return weeklyHours;
	}

	public void setWeeklyHours(Integer weeklyHours) {
		this.weeklyHours = weeklyHours;
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

	public TeacherSubjectEntity getTeacherSubject() {
		return teacherSubject;
	}

	public void setTeacherSubject(TeacherSubjectEntity teacherSubject) {
		this.teacherSubject = teacherSubject;
	}

	public ClassEntity getClassss() {
		return classss;
	}

	public void setClassss(ClassEntity classss) {
		this.classss = classss;
	}

	public List<GradingEntity> getGradings() {
		return gradings;
	}

	public void setGradings(List<GradingEntity> gradings) {
		this.gradings = gradings;
	}
	
	
	

}
