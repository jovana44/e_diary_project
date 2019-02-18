package com.iktpreobuka.ediary.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.ediary.controllers.util.RESTError;
import com.iktpreobuka.ediary.entities.ActivityEntity;
import com.iktpreobuka.ediary.entities.dto.ActivityDto;
import com.iktpreobuka.ediary.services.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	private final Logger logger =(Logger)LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){		
		try {
			List<ActivityEntity> activities= activityService.findAll();
			List<ActivityDto> listDto= new ArrayList<>();
			logger.debug("This is a debug message");
			logger.info("This is an info message");
			logger.warn("This is a warn message");
			logger.error("This is an error message");
			for(ActivityEntity activity: activities) {
				ActivityDto actDto= new ActivityDto(activity.getCode(), activity.getName());
				listDto.add(actDto);
			}
			return new ResponseEntity<List<ActivityDto>>(listDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			ActivityEntity activity= activityService.findById(id);
			if(activity==null) {
				return new ResponseEntity<RESTError>(new RESTError("Activity not found."), HttpStatus.BAD_REQUEST);
			}
			ActivityDto actDto= new ActivityDto(activity.getCode(), activity.getName());
			return new ResponseEntity<ActivityDto>(actDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody ActivityDto activityDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			ActivityEntity activity= activityService.create(new ActivityEntity(activityDto.getCode(), activityDto.getName()));
			ActivityDto activityDtoo = new ActivityDto(activity.getCode(), activity.getName());
			return new ResponseEntity<ActivityDto>(activityDtoo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ActivityDto activityDto,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			ActivityEntity activity= activityService.findById(id);
			if(activity==null) {
				return new ResponseEntity<RESTError>(new RESTError("Activity not found."), HttpStatus.BAD_REQUEST);
			}
			ActivityEntity act= activityService.update(id, new ActivityEntity(activityDto.getCode(), activityDto.getName()));
			ActivityDto actDto= new ActivityDto(act.getCode(), act.getName());
			return new ResponseEntity<ActivityDto>(actDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			ActivityEntity activity= activityService.findById(id);
			if(activity==null) {
				return new ResponseEntity<RESTError>(new RESTError("Activity not found."), HttpStatus.BAD_REQUEST);
			}
			ActivityEntity act= activityService.delete(id);
			if(act==null) {
				return new ResponseEntity<RESTError>(new RESTError("Activity is not been deleted, because one or more gradings related to it."), HttpStatus.FORBIDDEN);
			}
			ActivityDto actDto= new ActivityDto(activity.getCode(), activity.getName());
			return new ResponseEntity<ActivityDto>(actDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
}
