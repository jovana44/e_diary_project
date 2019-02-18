package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ParentEntity;
import com.iktpreobuka.ediary.entities.UserEntity;
import com.iktpreobuka.ediary.repositories.ParentRepository;
import com.iktpreobuka.ediary.repositories.UserRepository;

@Service
public class ParentServiceImpl implements ParentService{

	@Autowired
	private ParentRepository parentRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<ParentEntity> getAll(){
		return ( List<ParentEntity>) parentRepo.findAll();
	}
	
	@Override
	public ParentEntity getById(Long id) {
		return parentRepo.findById(id).get();
	}
	
	@Override
	public ParentEntity findByCode(String code) {
		return parentRepo.findByCode(code);
	}
	
	@Override
	public ParentEntity getByEmail(String email) {
		return parentRepo.findByEmail(email);
	}
	
	@Override
	public ParentEntity save(ParentEntity parent) {
		return parentRepo.save(parent);
	}
	
	@Override
	public ParentEntity createParentIfNotFound(ParentEntity newParent) {
		ParentEntity parent= parentRepo.findByEmail(newParent.getEmail());
		if(parent == null) {
			return save(newParent);
		}
		return parent;
	}

	@Override
	public ParentEntity put(Long id, ParentEntity newParent) {
		ParentEntity parent= getById(id);
		parent.setCode(newParent.getCode());
		parent.setFirstName(newParent.getFirstName());
		parent.setLastName(newParent.getLastName());
		parent.setEmail(newParent.getEmail());
		return save(parent);
	}
	
	@Override
	public ParentEntity delete(Long id) {
		ParentEntity parent= getById(id);
		if(!(parent.getStudents().isEmpty())) {
			return null;
		}
		for(UserEntity user: parent.getUsers()) {
			userRepo.delete(user);
		}
		parentRepo.delete(parent);
		return parent;
	}
	
}
