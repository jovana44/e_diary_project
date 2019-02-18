package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.PersonEntity;
import com.iktpreobuka.ediary.entities.URoleEntity;
import com.iktpreobuka.ediary.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByUserRoleAndPerson(URoleEntity uRole, PersonEntity person);
}
