package com.iktpreobuka.ediary.entities;

import java.time.LocalDate;
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
public class SchoolYearEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String code;
	@Version
	private Integer version;
	@Column(unique=true)
	private String name;
	private LocalDate yearStart;
	private LocalDate yearEnd;
	
	@OneToMany(mappedBy="schoolYear", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<SemesterEntity> semesters;
	
	@OneToMany(mappedBy="schYear", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<ClassEntity> classes;
	
	public SchoolYearEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchoolYearEntity(String code, String name, LocalDate yearStart, LocalDate yearEnd) {
		super();
		this.code = code;
		this.name = name;
		this.yearStart = yearStart;
		this.yearEnd = yearEnd;
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



	public LocalDate getYearStart() {
		return yearStart;
	}



	public void setYearStart(LocalDate yearStart) {
		this.yearStart = yearStart;
	}



	public LocalDate getYearEnd() {
		return yearEnd;
	}



	public void setYearEnd(LocalDate yearEnd) {
		this.yearEnd = yearEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SemesterEntity> getSemesters() {
		return semesters;
	}

	public void setSemesters(List<SemesterEntity> semesters) {
		this.semesters = semesters;
	}

	public List<ClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}
	
	
	
	
	
	
	

}
