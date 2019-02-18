package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.URoleEntity;
import com.iktpreobuka.ediary.repositories.URoleRepository;

@Service
public class URoleServiceImpl implements URoleService{

	@Autowired
	private URoleRepository uRoleRepo;
	
	@Override
	public List<URoleEntity> getAll(){
		return (List<URoleEntity>)uRoleRepo.findAll();
	}
	@Override
	public URoleEntity getById(Long id) {
		return uRoleRepo.findById(id).get();
	}
	@Override
	public URoleEntity save(URoleEntity role) {
		return uRoleRepo.save(role);
	}
	@Override
	public URoleEntity deleteById(Long id) {
		URoleEntity role= uRoleRepo.findById(id).get();
		if(!(role.getUsers().isEmpty())) {
			return null;
		}
		uRoleRepo.delete(role);
		return role;
	}
	@Override
	public URoleEntity put(Long id, URoleEntity newRole) {
		URoleEntity role= uRoleRepo.findById(id).get();
		role.setCode(newRole.getCode());
		role.setName(newRole.getName());
		return uRoleRepo.save(role);
	}
	
	@Override
	public URoleEntity findByName(String name) {
		return uRoleRepo.findByName(name);
	}
	

	
	
	
}
