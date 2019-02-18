package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.URoleEntity;

public interface URoleService {

	public List<URoleEntity> getAll();
	public URoleEntity getById(Long id);
	public URoleEntity save(URoleEntity role);
	public URoleEntity deleteById(Long id);
	public URoleEntity put(Long id, URoleEntity newRole);
	public URoleEntity findByName(String name);
}
