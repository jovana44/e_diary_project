package com.iktpreobuka.ediary.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public abstract class PersonEntity {

	@javax.persistence.Id
	@GeneratedValue
	private Long Id;
	@Column(unique=true)
	private String code;
	private String firstName;
	private String lastName;
	@Version
	private Integer version;
	
	@OneToMany(mappedBy="person", fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private List<UserEntity> users;

	public PersonEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PersonEntity(String code, String firstName, String lastName) {
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	public List<UserEntity> getUsers() {
		return users;
	}


	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
	
	
	
	
	
	
}
