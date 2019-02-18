package com.iktpreobuka.ediary.entities;

import java.time.LocalDate;
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
public class SemesterEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String code;
	@Version
	private Integer version;
	private Integer numberOfSem;
	private LocalDate semesterStart;
	private LocalDate semesterEnd;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="schoolYear")
	private SchoolYearEntity schoolYear;
	
	@OneToMany(mappedBy="semester", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<GradingEntity> gradings;
	
	
	public SemesterEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SemesterEntity(String code, Integer numberOfSem, LocalDate semesterStart, LocalDate semesterEnd) {
		super();
		this.code = code;
		this.numberOfSem = numberOfSem;
		this.semesterStart = semesterStart;
		this.semesterEnd = semesterEnd;
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

	public Integer getNumberOfSem() {
		return numberOfSem;
	}

	public void setNumberOfSem(Integer numberOfSem) {
		this.numberOfSem = numberOfSem;
	}

	public LocalDate getSemesterStart() {
		return semesterStart;
	}

	public void setSemesterStart(LocalDate semesterStart) {
		this.semesterStart = semesterStart;
	}

	public LocalDate getSemesterEnd() {
		return semesterEnd;
	}

	public void setSemesterEnd(LocalDate semesterEnd) {
		this.semesterEnd = semesterEnd;
	}

	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}

	public List<GradingEntity> getGradings() {
		return gradings;
	}

	public void setGradings(List<GradingEntity> gradings) {
		this.gradings = gradings;
	}
	
	
	
	

}
