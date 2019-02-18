package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.PersonEntity;

public interface PersonService {

	public PersonEntity findByCode(String code);
	public List<PersonEntity> getAll();
	public PersonEntity getById(Long id);
}
