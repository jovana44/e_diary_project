package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.SubjectEntity;

public interface SubjectService {

	public List<SubjectEntity> getAll();
	public SubjectEntity getById(Long id);
	public SubjectEntity save(SubjectEntity subject);
	public SubjectEntity delete(Long id);
	public SubjectEntity update(Long id, SubjectEntity newSubject);
	public SubjectEntity createIfNotFound(String code, String name);
	public SubjectEntity getByName(String name);
}
