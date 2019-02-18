package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Long> {

	TeacherEntity getByCode(String code);
}
