package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Long> {

	AdminEntity findByCode(String code);
}
