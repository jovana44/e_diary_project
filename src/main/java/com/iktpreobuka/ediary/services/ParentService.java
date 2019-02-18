package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.ParentEntity;

public interface ParentService {
	
	public List<ParentEntity> getAll();
	public ParentEntity getById(Long id);
	public ParentEntity findByCode(String code);
	public ParentEntity put(Long id, ParentEntity newParent);
	public ParentEntity delete(Long id);
	public ParentEntity createParentIfNotFound(ParentEntity newParent);
	public ParentEntity getByEmail(String email);
	public ParentEntity save(ParentEntity parent);
}
