package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Long> {

	ParentEntity findByEmail(String email);
	ParentEntity findByCode(String code);
}
