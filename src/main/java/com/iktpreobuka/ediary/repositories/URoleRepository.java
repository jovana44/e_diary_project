package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.URoleEntity;

public interface URoleRepository extends CrudRepository<URoleEntity, Long> {

	URoleEntity findByName(String name);
}
