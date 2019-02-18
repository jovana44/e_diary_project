package com.iktpreobuka.ediary.entities;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;
	@Column(unique=true)
	private String password;
	@Column(unique=true)
	private String username;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="userRole")
	private URoleEntity userRole;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="person")
	private PersonEntity person;
	
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserEntity(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}
	

	public UserEntity(String password, String username, URoleEntity userRole, PersonEntity person) {
		super();
		this.password = password;
		this.username = username;
		this.userRole = userRole;
		this.person = person;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public URoleEntity getUserRole() {
		return userRole;
	}

	public void setUserRole(URoleEntity userRole) {
		this.userRole = userRole;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
	}
	
	
	
	
	
}
