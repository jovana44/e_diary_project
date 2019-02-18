package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.PersonEntity;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

	PersonEntity findByCode(String code);
}
