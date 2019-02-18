package com.iktpreobuka.ediary.entities;

import javax.persistence.Entity;

@Entity
public class AdminEntity extends PersonEntity{

	public AdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminEntity(String code, String firstName, String lastName) {
		super(code, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

	
}
