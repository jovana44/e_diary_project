package com.iktpreobuka.ediary.entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class ClassEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String code;
	@Version
	private Integer version;
	private Integer year;
	private Integer numberOfClass;
	
	@OneToMany(mappedBy="classs", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<StudentClassEntity> studentsClass;
	
	@OneToMany(mappedBy="classss", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<TeacherSubjectClassEntity> teacherSubjectClass;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="schYear")
	private SchoolYearEntity schYear;
	
	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassEntity(String code, Integer year, Integer numberOfClass, SchoolYearEntity schYear) {
		super();
		this.code = code;
		this.year = year;
		this.numberOfClass = numberOfClass;
		this.schYear = schYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getNumberOfClass() {
		return numberOfClass;
	}

	public void setNumberOfClass(Integer numberOfClass) {
		this.numberOfClass = numberOfClass;
	}



	public List<StudentClassEntity> getStudentsClass() {
		return studentsClass;
	}


	public void setStudentsClass(List<StudentClassEntity> studentsClass) {
		this.studentsClass = studentsClass;
	}


	public List<TeacherSubjectClassEntity> getTeacherSubjectClass() {
		return teacherSubjectClass;
	}

	public void setTeacherSubjectClass(List<TeacherSubjectClassEntity> teacherSubjectClass) {
		this.teacherSubjectClass = teacherSubjectClass;
	}

	public SchoolYearEntity getSchYear() {
		return schYear;
	}

	public void setSchYear(SchoolYearEntity schYear) {
		this.schYear = schYear;
	}

	
	
	
}
