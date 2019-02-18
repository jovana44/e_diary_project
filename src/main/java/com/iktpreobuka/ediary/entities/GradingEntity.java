package com.iktpreobuka.ediary.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;


@Entity
public class GradingEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	private Integer mark;
	private LocalDate date;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="activity")
	private ActivityEntity activity;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="student")
	private StudentEntity student;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="teacherSubjectClass")
	private TeacherSubjectClassEntity teacherSubjectClass;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="semester")
	private SemesterEntity semester;

	public GradingEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public GradingEntity(Integer mark, ActivityEntity activity, StudentEntity student,
			TeacherSubjectClassEntity teacherSubjectClass, SemesterEntity semester) {
		super();
		this.mark = mark;
		this.activity = activity;
		this.student = student;
		this.teacherSubjectClass = teacherSubjectClass;
		this.semester = semester;
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

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ActivityEntity getActivity() {
		return activity;
	}

	public void setActivity(ActivityEntity activity) {
		this.activity = activity;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeacherSubjectClassEntity getTeacherSubjectClass() {
		return teacherSubjectClass;
	}

	public void setTeacherSubjectClass(TeacherSubjectClassEntity teacherSubjectClass) {
		this.teacherSubjectClass = teacherSubjectClass;
	}

	public SemesterEntity getSemester() {
		return semester;
	}

	public void setSemester(SemesterEntity semester) {
		this.semester = semester;
	}
	
	
	
}
