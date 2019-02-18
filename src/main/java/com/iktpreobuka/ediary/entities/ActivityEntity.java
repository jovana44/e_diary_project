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
public class ActivityEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String code;
	@Version
	private Integer version;
	@Column(unique=true)
	private String name;
	
	@OneToMany(mappedBy="activity", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<GradingEntity> gradingEntity;
	
	public ActivityEntity() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ActivityEntity(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public List<GradingEntity> getGradingEntity() {
		return gradingEntity;
	}

	public void setGradingEntity(List<GradingEntity> gradingEntity) {
		this.gradingEntity = gradingEntity;
	}
	
	
	
	
}
