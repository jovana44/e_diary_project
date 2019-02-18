package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.AdminEntity;
import com.iktpreobuka.ediary.entities.UserEntity;
import com.iktpreobuka.ediary.repositories.AdminRepository;
import com.iktpreobuka.ediary.repositories.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<AdminEntity> findAll(){
		return (List<AdminEntity>) adminRepo.findAll();
	}
	
	@Override
	public AdminEntity findByCode(String code) {
		return adminRepo.findByCode(code);
	}
	
	@Override
	public AdminEntity getById(Long id) {
		return adminRepo.findById(id).get();
	}
	
	@Override
	public AdminEntity save(AdminEntity admin) {
		return adminRepo.save(admin);
	}
	
	@Override
	public AdminEntity delete(Long id) {
		AdminEntity admin= getById(id);
		for(UserEntity user:admin.getUsers()) {
			userRepo.delete(user);
		}
		adminRepo.delete(admin);
		return admin;
	}
	
	@Override
	public AdminEntity update(Long id, AdminEntity newAdmin) {
		AdminEntity admin= getById(id);
		admin.setCode(newAdmin.getCode());
		admin.setFirstName(newAdmin.getFirstName());
		admin.setLastName(newAdmin.getLastName());
		return adminRepo.save(admin);
	}
}
