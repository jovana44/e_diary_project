package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.ActivityEntity;

public interface ActivityService {

	public List<ActivityEntity> findAll();
	
	public ActivityEntity findById(Long id);
	
	public ActivityEntity findByName(String name);
	
	public ActivityEntity delete(Long id);
	
	public ActivityEntity create(ActivityEntity activity);
	
	public ActivityEntity update(Long id, ActivityEntity newActivity);
}
