package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.AdminEntity;

public interface AdminService {

	public List<AdminEntity> findAll();
	public AdminEntity findByCode(String code);
	public AdminEntity getById(Long id);
	public AdminEntity save(AdminEntity admin);
	public AdminEntity delete(Long id);
	public AdminEntity update(Long id, AdminEntity newAdmin);
}
