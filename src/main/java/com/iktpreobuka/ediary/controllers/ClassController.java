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
import com.iktpreobuka.ediary.entities.SchoolYearEntity;
import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.entities.dto.ClassDto;
import com.iktpreobuka.ediary.entities.dto.SchoolYearDto;
import com.iktpreobuka.ediary.entities.dto.SubjectDto;
import com.iktpreobuka.ediary.entities.dto.TeacherDto;
import com.iktpreobuka.ediary.entities.dto.TeacherSubjectClassDto;
import com.iktpreobuka.ediary.entities.dto.TeacherSubjectDto;
import com.iktpreobuka.ediary.services.ClassService;
import com.iktpreobuka.ediary.services.SchoolYearService;
import com.iktpreobuka.ediary.services.SubjectService;
import com.iktpreobuka.ediary.services.TeacherService;
import com.iktpreobuka.ediary.services.TeacherSubjectService;

@RestController
@RequestMapping("/classes")
public class ClassController {
	
	private final Logger logger =(Logger)LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClassService classService;
	@Autowired
	private SchoolYearService schYearService;
	@Autowired
	private TeacherSubjectService tsService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		try {
			List<ClassEntity> classes= classService.getAll();
			List<ClassDto> classesDto=new ArrayList<>();
			logger.debug("This is a debug message");
			logger.info("This is an info message");
			logger.warn("This is a warn message");
			logger.error("This is an error message");
			for(ClassEntity classs: classes) {
				SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
				ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto);
				classesDto.add(classDto);
			}
			return new ResponseEntity<List<ClassDto>>(classesDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			ClassEntity classs=classService.getById(id);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("No class found."), HttpStatus.NOT_FOUND);
			}
			List<TeacherSubjectDto> teacherSubjectsDto= new ArrayList<>();
	        for(TeacherSubjectClassEntity tsc: classs.getTeacherSubjectClass()) {
	        	TeacherDto teacherDto = new TeacherDto(tsc.getTeacherSubject().getTeacher().getCode(), tsc.getTeacherSubject().getTeacher().getFirstName(), tsc.getTeacherSubject().getTeacher().getLastName());
	        	SubjectDto subjectDto = new SubjectDto(tsc.getTeacherSubject().getSubject().getName());
	        	TeacherSubjectDto teacherSubjectDto= new TeacherSubjectDto(teacherDto, subjectDto, tsc.getWeeklyHours());
	        	teacherSubjectsDto.add(teacherSubjectDto);
	        }
			SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
			ClassDto classDto= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto, teacherSubjectsDto);
             return new ResponseEntity<ClassDto>(classDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//kreiranje odeljenja i povezivanje sa odgovarajucom skolskom godinom
	@RequestMapping(method=RequestMethod.POST)
	public  ResponseEntity<?> create(@Valid @RequestBody ClassDto classDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			SchoolYearEntity schoolYear= schYearService.findByCode(classDto.getSchYearDto().getCode());
			if(schoolYear==null) {
				return new ResponseEntity<RESTError>(new RESTError("School year not found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity newClass= new ClassEntity(classDto.getCode(), classDto.getYear(), classDto.getNumberOfClass(), schoolYear);
			newClass= classService.save(newClass);
			SchoolYearDto schYearDto= new SchoolYearDto(newClass.getSchYear().getCode(), newClass.getSchYear().getName(), newClass.getSchYear().getYearStart(), newClass.getSchYear().getYearEnd());
			ClassDto newClassDto= new ClassDto(newClass.getCode(), newClass.getYear(), newClass.getNumberOfClass(), schYearDto);
			return new ResponseEntity<ClassDto>(newClassDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	//izmena podataka o odeljenju i promena skolske godine tog odeljenja
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ClassDto classDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			ClassEntity classs=classService.getById(id);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("No class found."), HttpStatus.NOT_FOUND);
			}
			SchoolYearEntity schYear=schYearService.findByCode(classDto.getSchYearDto().getCode());
			if(schYear==null) {
				return new ResponseEntity<RESTError>(new RESTError("School year not found."), HttpStatus.NOT_FOUND);
			}
			classs=classService.update(id, new ClassEntity(classDto.getCode(), classDto.getYear(), classDto.getNumberOfClass(), schYear));
			SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
			ClassDto classDtoo= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto);
             return new ResponseEntity<ClassDto>(classDtoo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//dodavanje skolskih predmeta u obliku liste, nastavnik-predmet-fond
	@RequestMapping(method=RequestMethod.POST, value="/{id}/addsubjects")
	public ResponseEntity<?> addSubjects(@PathVariable Long id, @Valid @RequestBody List<TeacherSubjectDto> tsListDto, BindingResult result ){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			ClassEntity classs=classService.getById(id);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("No class found."), HttpStatus.NOT_FOUND);
			}
			for(TeacherSubjectDto tsDto: tsListDto) {
				TeacherEntity teacher= teacherService.getByCode(tsDto.getTeacherDto().getCode());
				if(teacher==null) {
					return new ResponseEntity<RESTError>(new RESTError("One or more teachers not found."), HttpStatus.NOT_FOUND);
				}
				SubjectEntity subject= subjectService.getByName(tsDto.getSubjectDto().getName());
				if(subject==null) {
					return new ResponseEntity<RESTError>(new RESTError("One or more subjects not found."), HttpStatus.NOT_FOUND);
				}
				TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
				if(ts==null) {
					return new ResponseEntity<RESTError>(new RESTError("One or more teachers dont teach this subjects."), HttpStatus.BAD_REQUEST);
				}
			}
			List<TeacherSubjectDto> teacherSubjectsDto= classService.addTeacherSubjects(id, tsListDto);
			SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
			ClassDto classDto = new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto);
			TeacherSubjectClassDto tscDto= new TeacherSubjectClassDto(classDto, teacherSubjectsDto);
			return new ResponseEntity<TeacherSubjectClassDto>(tscDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//obrisi jedan skolski predmet, tog odeljenja
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}/deletesubject")
	public ResponseEntity<?> deleteSubject(@PathVariable Long id, @Valid @RequestBody TeacherSubjectDto tsDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			ClassEntity classs=classService.getById(id);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("No class found."), HttpStatus.NOT_FOUND);
			}
			TeacherEntity teacher= teacherService.getByCode(tsDto.getTeacherDto().getCode());
			if(teacher==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher not found."), HttpStatus.NOT_FOUND);
			}
			SubjectEntity subject= subjectService.getByName(tsDto.getSubjectDto().getName());
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("Subject not found."), HttpStatus.NOT_FOUND);
			}
			TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
			if(ts==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher dont teach this subject."), HttpStatus.BAD_REQUEST);
			}
			
			TeacherSubjectDto teacherSubjectDto= classService.deleteSubject(id, tsDto);
			if(teacherSubjectDto==null) {
				return new ResponseEntity<RESTError>(new RESTError("Subject is not been deleted, because it has grades."), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TeacherSubjectDto>(teacherSubjectDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//izbrisi odeljenje ako nema nijednog ucenika
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			ClassEntity classs=classService.getById(id);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("No class found."), HttpStatus.NOT_FOUND);
			}
			classs=classService.delete(id);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class is not been deleted, because it has one or more students."), HttpStatus.FORBIDDEN);
			}
			SchoolYearDto schYearDto= new SchoolYearDto(classs.getSchYear().getCode(), classs.getSchYear().getName(), classs.getSchYear().getYearStart(), classs.getSchYear().getYearEnd());
			ClassDto classDtoo= new ClassDto(classs.getCode(), classs.getYear(), classs.getNumberOfClass(), schYearDto);
             return new ResponseEntity<ClassDto>(classDtoo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}
