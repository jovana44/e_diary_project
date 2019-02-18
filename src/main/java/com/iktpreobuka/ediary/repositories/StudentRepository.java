package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

	StudentEntity findByCode(String code);
}
