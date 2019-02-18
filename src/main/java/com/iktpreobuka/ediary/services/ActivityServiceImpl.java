package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ActivityEntity;
import com.iktpreobuka.ediary.repositories.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	private ActivityRepository activityRepo;
	
	@Override
	public List<ActivityEntity> findAll(){
		return (List<ActivityEntity>) activityRepo.findAll();
	}
	
	@Override
	public ActivityEntity findById(Long id) {
		return activityRepo.findById(id).get();
	}
	
	@Override
	public ActivityEntity findByName(String name) {
		return activityRepo.findByName(name);
	}
	
	@Override
	public ActivityEntity delete(Long id) {
		ActivityEntity activity= findById(id);
		if(activity.getGradingEntity()!=null) {
			return null;
		}
		activityRepo.delete(activity);
		return activity;
	}
	
	@Override
	public ActivityEntity create(ActivityEntity activity) {
		return activityRepo.save(activity);
	}
	
	@Override
	public ActivityEntity update(Long id, ActivityEntity newActivity) {
		ActivityEntity activity= findById(id);
		activity.setCode(newActivity.getCode());
		activity.setName(newActivity.getName());
		return activityRepo.save(activity);
	}
	
	
}
