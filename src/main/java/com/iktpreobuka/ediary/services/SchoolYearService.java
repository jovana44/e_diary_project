package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.SchoolYearEntity;

public interface SchoolYearService {

	public List<SchoolYearEntity> getAll();
	public SchoolYearEntity getById(Long id);
	public SchoolYearEntity delete(Long id);
	public SchoolYearEntity findByCode(String code);
	public SchoolYearEntity save(SchoolYearEntity schYear);
	public SchoolYearEntity createSchYearIfNotFound(SchoolYearEntity schYear);
	public SchoolYearEntity update(SchoolYearEntity schYear, SchoolYearEntity newSchYear);
}
