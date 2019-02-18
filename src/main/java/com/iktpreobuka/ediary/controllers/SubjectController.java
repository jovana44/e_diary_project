package com.iktpreobuka.ediary.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.dto.SubjectDto;
import com.iktpreobuka.ediary.services.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		try {
			List<SubjectEntity> subjects= subjectService.getAll();
			List<SubjectDto> subjectsDto= new ArrayList<>();
		   for(SubjectEntity subject: subjects) {
			   SubjectDto subjectDto=new SubjectDto(subject.getCode(), subject.getName());
			   subjectsDto.add(subjectDto);
		   }
			return new ResponseEntity<List<SubjectDto>>(subjectsDto, HttpStatus.OK);
	   }catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			SubjectEntity subject=subjectService.getById(id);
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("No subject found."), HttpStatus.NOT_FOUND);
			}
			SubjectDto subjectDto=new SubjectDto(subject.getCode(), subject.getName());
			return new ResponseEntity<SubjectDto>(subjectDto, HttpStatus.OK);
	   }catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectDto subjectDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			SubjectEntity subject= subjectService.save(new SubjectEntity(subjectDto.getCode(), subjectDto.getName()));
			SubjectDto subjectDtoo=new SubjectDto(subject.getCode(), subject.getName());
			return new ResponseEntity<SubjectDto>(subjectDtoo, HttpStatus.OK);
	   }catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectDto subjectDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			SubjectEntity subject=subjectService.getById(id);
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("No subject found."), HttpStatus.NOT_FOUND);
			}
			subject= subjectService.update(id, new SubjectEntity(subjectDto.getCode(), subjectDto.getName()));
			SubjectDto subjectDtoo=new SubjectDto(subject.getCode(), subject.getName());
			return new ResponseEntity<SubjectDto>(subjectDtoo, HttpStatus.OK);
	   }catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long id){
		try {
			SubjectEntity subject=subjectService.getById(id);
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("No subject found."), HttpStatus.NOT_FOUND);
			}
			subject=subjectService.delete(id);
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("Subject is not been deleted, because one or more teachers releted to it."), HttpStatus.FORBIDDEN);
			}
			SubjectDto subjectDtoo=new SubjectDto(subject.getCode(), subject.getName());
			return new ResponseEntity<SubjectDto>(subjectDtoo, HttpStatus.OK);
	   }catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}
