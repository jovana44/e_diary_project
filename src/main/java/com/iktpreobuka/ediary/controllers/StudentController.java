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
import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.ParentEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.dto.ClassDto;
import com.iktpreobuka.ediary.entities.dto.ParentDto;
import com.iktpreobuka.ediary.entities.dto.SchoolYearDto;
import com.iktpreobuka.ediary.entities.dto.StudentDto;
import com.iktpreobuka.ediary.services.ClassService;
import com.iktpreobuka.ediary.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	private final Logger logger =(Logger)LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassService classService;
	
	//pribavljanje svih ucenika i njihovih trenutnih odeljenja
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllStudents(){
		try {
			List<StudentEntity> students = studentService.getAll();
			List<StudentDto> studentsDto= new ArrayList<>();
			logger.debug("This is a debug message");
			logger.info("This is an info message");
			logger.warn("This is a warn message");
			logger.error("This is an error message");
			for(StudentEntity student: students) {
				ParentDto parentDto= new ParentDto(student.getParent().getCode(), student.getParent().getFirstName(), student.getParent().getLastName(), student.getParent().getEmail());
				ClassEntity classs= classService.findClassInPresentForStudent(student);
				ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass());
				StudentDto studentDto= new StudentDto(student.getCode(), student.getFirstName(), student.getLastName(), parentDto, classDto);
				studentsDto.add(studentDto);
			}
			    return new ResponseEntity<List<StudentDto>>(studentsDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//pribavljanje ucenika i njegovog trenutnog odeljenja
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			StudentEntity student= studentService.getById(id);
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
	        ParentDto parentDto=new ParentDto(student.getParent().getCode(), student.getParent().getFirstName(), student.getParent().getLastName(), student.getParent().getEmail());
	        ClassEntity classs= classService.findClassInPresentForStudent(student);
	        ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass());
			StudentDto studentDto= new StudentDto(student.getCode(), student.getFirstName(), student.getLastName(), parentDto, classDto);
			return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//kreiranje ucenika i njegovog roditelja (ako roditelj vec nije kreiran), i dodavanje jednog odeljenja (kreiranje studentClass)
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDto studentDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			ClassEntity classs=classService.getByCode(studentDto.getClassDto().getCode());
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class not found"), HttpStatus.NOT_FOUND);
			}
			if(classService.isPresent(classs)==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class not found, this school year."), HttpStatus.NOT_FOUND);
			}
			ParentEntity parent=new ParentEntity(studentDto.getParentDto().getCode(), studentDto.getParentDto().getFirstName(), 
                                                studentDto.getParentDto().getLastName(), studentDto.getParentDto().getEmail());
			StudentEntity student= new StudentEntity(studentDto.getCode(), studentDto.getFirstName(), studentDto.getLastName());
			StudentEntity studentt=studentService.create(student, parent, classs);
			SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), 
					                                classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
			ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto);
			ParentDto parentDto= new ParentDto(studentt.getParent().getCode(), studentt.getParent().getFirstName(),
					                      studentt.getParent().getLastName(), studentt.getParent().getEmail());
			StudentDto studentDtoo= new StudentDto(student.getCode(), student.getFirstName(), student.getLastName(), 
					                                  parentDto, classDto);
			return new ResponseEntity<StudentDto>(studentDtoo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//izmena svih podataka o uceniku i roditelju i promena odeljenja koje pohadja
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			StudentEntity student= studentService.getById(id);
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity classs=classService.getByCode(studentDto.getClassDto().getCode());
			if(classService.isPresent(classs)==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class not found, this school year."), HttpStatus.NOT_FOUND);
			}
			ParentEntity parent= new ParentEntity(studentDto.getParentDto().getCode(), studentDto.getParentDto().getFirstName(),
				                	studentDto.getParentDto().getLastName(), studentDto.getParentDto().getEmail());
			StudentEntity studentt= studentService.update(id, new StudentEntity(studentDto.getCode(), studentDto.getFirstName(), studentDto.getLastName(), parent), classs);
			SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), 
                                       classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
             ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto);
             ParentDto parentDto= new ParentDto(parent.getCode(), parent.getFirstName(), parent.getLastName(), parent.getEmail());
             StudentDto studentDtoo= new StudentDto(studentt.getCode(), studentt.getFirstName(), studentt.getLastName(), parentDto, classDto);
             return new ResponseEntity<StudentDto>(studentDtoo, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//dodavanje novog odeljenja uceniku, pod uslovom da trenutno nema odeljenje (u pozadini se kreira studentClass)
	@RequestMapping(method=RequestMethod.PUT, value="/{id}/addclass")
	public ResponseEntity<?> addClass(@PathVariable Long id,@Valid @RequestBody ClassDto classDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			StudentEntity stud= studentService.getById(id);
			if(stud==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
			if(classService.findClassInPresentForStudent(stud)!=null) {
				return new ResponseEntity<RESTError>(new RESTError("A new class cannot be added to the student, because he already have a class, at the moment."), HttpStatus.BAD_REQUEST);
			}
			ClassEntity classs= classService.getByCode(classDto.getCode());
			if(classService.isPresent(classs)==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class not found this school year."), HttpStatus.NOT_FOUND);
			}
			StudentEntity student= studentService.addClass(id, classs);
			ParentDto parentDto=new ParentDto(student.getParent().getCode(), student.getParent().getFirstName(), student.getParent().getLastName(), student.getParent().getEmail());
		    ClassDto classDtoo= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass());
			StudentDto studentDto= new StudentDto(student.getCode(), student.getFirstName(), student.getLastName(), parentDto, classDtoo);
			return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//brisanje ucenika pod uslovom da nema ocena i ne ide u nijedno odeljenje
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			StudentEntity student=studentService.getById(id);
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity classs= classService.findClassInPresentForStudent(student);
			ParentEntity parent=student.getParent();
			StudentEntity studentt= studentService.deleteById(id);
			if(studentt==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student is not been deleted, because he has gradings."), HttpStatus.FORBIDDEN);
			}
				ParentDto parentDto=new ParentDto(parent.getCode(), parent.getFirstName(), parent.getLastName(), parent.getEmail());
			    ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass());
				StudentDto studentDto= new StudentDto(student.getCode(), student.getFirstName(), student.getLastName());
				studentDto.setParentDto(parentDto);
				studentDto.setClassDto(classDto);
				return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}
