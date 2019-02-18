package com.iktpreobuka.ediary.services;

import java.util.List;
import com.iktpreobuka.ediary.entities.PersonEntity;
import com.iktpreobuka.ediary.entities.URoleEntity;
import com.iktpreobuka.ediary.entities.UserEntity;

public interface UserService {

	public List<UserEntity> getAll();
	public UserEntity getById(Long id);
	public UserEntity save(UserEntity user);

	public UserEntity createUser(UserEntity user, PersonEntity person);
	
	public UserEntity update(Long id, UserEntity newUser);
	
	
	public UserEntity updateRole(Long id, URoleEntity role);
	
	public UserEntity delete(Long id);
}
