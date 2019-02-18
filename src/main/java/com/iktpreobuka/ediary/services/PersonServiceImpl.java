package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.PersonEntity;
import com.iktpreobuka.ediary.repositories.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepo;
	
	@Override
	public List<PersonEntity> getAll(){
		return (List<PersonEntity>)personRepo.findAll();
	}
	
	@Override
	public PersonEntity getById(Long id) {
		return personRepo.findById(id).get();
	}
	
	@Override
	public PersonEntity findByCode(String code) {
		return personRepo.findByCode(code);
	}
	
}
