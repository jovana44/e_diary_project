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
import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.entities.dto.SubjectDto;
import com.iktpreobuka.ediary.entities.dto.TeacherDto;
import com.iktpreobuka.ediary.services.SubjectService;
import com.iktpreobuka.ediary.services.TeacherService;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

	private final Logger logger =(Logger)LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAllTeachers(){
		try {
			List<TeacherEntity> teachers= teacherService.getAll();
			List<TeacherDto> teachersDto=new ArrayList<>();
			
			for(TeacherEntity teacher: teachers) {
				List<SubjectDto> subjectsDto=new ArrayList<>();
					for(TeacherSubjectEntity teacherSubject: teacher.getTeachersSubjects()) {
					SubjectDto subjectDto= new SubjectDto(teacherSubject.getSubject().getCode(), teacherSubject.getSubject().getName());
					subjectsDto.add(subjectDto);
					}
					TeacherDto teacherDto=new TeacherDto(teacher.getCode(), teacher.getFirstName(), teacher.getLastName(), subjectsDto);
                    teachersDto.add(teacherDto);
		    }
			return new ResponseEntity<List<TeacherDto>>(teachersDto, HttpStatus.OK);
		}catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			TeacherEntity teacher= teacherService.getById(id);
			if(teacher==null) {
				return new ResponseEntity<RESTError>(new RESTError("No teacher found"), HttpStatus.NOT_FOUND);
			}
			List<SubjectDto> subjectsDto=new ArrayList<>();
			for(TeacherSubjectEntity teacherSubject: teacher.getTeachersSubjects()) {
				SubjectDto subjectDto= new SubjectDto(teacherSubject.getSubject().getCode(), teacherSubject.getSubject().getName());
				subjectsDto.add(subjectDto);
				}
			TeacherDto teacherDto=new TeacherDto(teacher.getCode(), teacher.getFirstName(), teacher.getLastName(), subjectsDto);
			return new ResponseEntity<TeacherDto>(teacherDto, HttpStatus.OK);
		}catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	//kreiramo nastavika i listu njegovih predmeta (tj. teacherSubject)
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createTeacher(@Valid @RequestBody TeacherDto teacherDto,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			TeacherEntity teacher=new TeacherEntity(teacherDto.getCode(), teacherDto.getFirstName(), teacherDto.getLastName());
			List<SubjectEntity> subjects=new ArrayList<>();
			for(SubjectDto subjectDto: teacherDto.getSubjectsDto()) {
				SubjectEntity subject=subjectService.getByName(subjectDto.getName());
				if(subject==null) {
					return new ResponseEntity<RESTError>(new RESTError("One or more subjects not found"), HttpStatus.NOT_FOUND);
				}
				subjects.add(subject);
			}
			TeacherEntity teacherr= teacherService.createTeacher(teacher, subjects);
						List<SubjectDto> subjectsDto= new ArrayList<>();
			for(TeacherSubjectEntity teacherSubject: teacherr.getTeachersSubjects()) {
				SubjectDto subjectDtoo= new SubjectDto(teacherSubject.getSubject().getCode(), teacherSubject.getSubject().getName());
			    subjectsDto.add(subjectDtoo);
			}
			TeacherDto teacherDtoo= new TeacherDto(teacherr.getCode(), teacherr.getFirstName(), teacherr.getLastName(), subjectsDto);
		    return new ResponseEntity<TeacherDto>(teacherDtoo, HttpStatus.OK);
		}catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	//izmena podataka o nastavniku i listi predmeta
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherDto teacherDto,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			if(teacherService.getById(id)==null) {
				return new ResponseEntity<RESTError>(new RESTError("No teacher found."), HttpStatus.NOT_FOUND);
			}
			List<SubjectEntity> subjects = new ArrayList<>();
			for(SubjectDto subjectDto: teacherDto.getSubjectsDto()) {
				SubjectEntity subject=subjectService.getByName(subjectDto.getName());
				if(subject==null) {
					return new ResponseEntity<RESTError>(new RESTError("One or more subjects not found"), HttpStatus.NOT_FOUND);
				}
				subjects.add(subject);
			}
			TeacherEntity newTeacher=new TeacherEntity(teacherDto.getCode(), teacherDto.getFirstName(), teacherDto.getLastName());
			TeacherEntity teacherr= teacherService.update(id, newTeacher , subjects);
			List<SubjectDto> subjectsDto= new ArrayList<>();
			for(TeacherSubjectEntity teacherSubject: teacherr.getTeachersSubjects()) {
				SubjectDto subjectDtoo= new SubjectDto(teacherSubject.getSubject().getCode(), teacherSubject.getSubject().getName());
			    subjectsDto.add(subjectDtoo);
			}
			TeacherDto teacherDtoo= new TeacherDto(teacherr.getCode(), teacherr.getFirstName(), teacherr.getLastName(), subjectsDto);
		    return new ResponseEntity<TeacherDto>(teacherDtoo, HttpStatus.OK);
		}catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

	//izbrisi nastavnika ako nije predavao ili ocenjivao nijedno odeljenje
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Long id){
		try {
			TeacherEntity teacher= teacherService.getById(id);
			if(teacher==null) {
				return new ResponseEntity<RESTError>(new RESTError("No teacher found."), HttpStatus.NOT_FOUND);
			}
			TeacherEntity teacherr= teacherService.delete(id);
			if(teacherr==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher is not deleted because, he was teach or grading one or more classes."), HttpStatus.FORBIDDEN);
			}
			List<SubjectDto> subjectsDto= new ArrayList<>();
			for(TeacherSubjectEntity teacherSubject: teacher.getTeachersSubjects()) {
				SubjectDto subjectDtoo= new SubjectDto(teacherSubject.getSubject().getCode(), teacherSubject.getSubject().getName());
			    subjectsDto.add(subjectDtoo);
			}
			TeacherDto teacherDto=new TeacherDto(teacher.getCode(), teacher.getFirstName(), teacher.getLastName(), subjectsDto);
			return new ResponseEntity<TeacherDto>(teacherDto, HttpStatus.OK);
		}catch(Exception e){
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	
}
