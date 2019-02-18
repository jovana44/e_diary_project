package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.SchoolYearEntity;

public interface SchoolYearRepository extends CrudRepository<SchoolYearEntity, Long> {

	SchoolYearEntity findByCode(String code);
}
