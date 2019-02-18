package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.ActivityEntity;

public interface ActivityRepository extends CrudRepository<ActivityEntity, Long> {

	ActivityEntity findByName(String name);
}
