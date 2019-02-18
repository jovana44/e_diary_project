package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {

	SubjectEntity findByCode(String code);
	SubjectEntity findByName(String name);
}
