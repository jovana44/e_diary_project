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
import com.iktpreobuka.ediary.entities.SchoolYearEntity;
import com.iktpreobuka.ediary.entities.SemesterEntity;
import com.iktpreobuka.ediary.entities.dto.SchoolYearDto;
import com.iktpreobuka.ediary.entities.dto.SemesterDto;
import com.iktpreobuka.ediary.services.SemesterService;

@RestController
@RequestMapping("/semesters")
public class SemesterController {

	@Autowired
	private SemesterService semesterService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findAll(){
		try {
			List<SemesterEntity> semesters= semesterService.getAll();
			List<SemesterDto> semestersDto=new ArrayList<>();
			for(SemesterEntity semester:semesters) {
				SchoolYearDto schYearDto=new SchoolYearDto(semester.getSchoolYear().getCode(), semester.getSchoolYear().getName(),
					                             	semester.getSchoolYear().getYearStart(), semester.getSchoolYear().getYearEnd());
			    SemesterDto semesterDto= new SemesterDto(semester.getCode(), semester.getNumberOfSem(), semester.getSemesterStart(),
			    		                                semester.getSemesterEnd(), schYearDto);
				semestersDto.add(semesterDto);
			}
		    return new ResponseEntity<List<SemesterDto>>(semestersDto, HttpStatus.OK);
	    }catch(Exception e) {
	    	return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		try {
			SemesterEntity semester= semesterService.getById(id);
			SchoolYearDto schYearDto=new SchoolYearDto(semester.getSchoolYear().getCode(), semester.getSchoolYear().getName(),
                                                	semester.getSchoolYear().getYearStart(), semester.getSchoolYear().getYearEnd());
	        SemesterDto semesterDto= new SemesterDto(semester.getCode(), semester.getNumberOfSem(), semester.getSemesterStart(),
                                                       semester.getSemesterEnd(), schYearDto);
            return new ResponseEntity<SemesterDto>(semesterDto, HttpStatus.OK);
        }catch(Exception e) {
	     return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
       }
	}
	//kreira novi semestar i novu skolsku godinu ako je ne pronadje
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createSemester(@Valid @RequestBody SemesterDto semesterDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			SchoolYearEntity schYear= new SchoolYearEntity(semesterDto.getSchYearDto().getCode(), semesterDto.getSchYearDto().getName(),
					                                      semesterDto.getSchYearDto().getYearStart(), semesterDto.getSchYearDto().getYearEnd());
		    SemesterEntity semester = new SemesterEntity(semesterDto.getCode(), semesterDto.getNumberOfSem(), semesterDto.getSemesterStart(), semesterDto.getSemesterEnd());
		    SemesterEntity semesterr = semesterService.createSemester(semester, schYear);
		    SchoolYearDto schYearDto= new SchoolYearDto(semesterr.getSchoolYear().getCode(), semesterr.getSchoolYear().getName(), 
		    	                              	semesterr.getSchoolYear().getYearStart(), semesterr.getSchoolYear().getYearEnd());
		    SemesterDto semesterDtoo= new SemesterDto(semesterr.getCode(), semesterr.getNumberOfSem(), semesterr.getSemesterStart(), 
		    		                                  semesterr.getSemesterEnd(), schYearDto);
		    return new ResponseEntity<SemesterDto>(semesterDtoo, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//izmena podataka o semestru i skolskoj godini
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> updateSemester(@PathVariable Long id, @Valid @RequestBody SemesterDto semesterDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			SemesterEntity semester= semesterService.getById(id);
			if(semester==null) {
				return new ResponseEntity<RESTError>(new RESTError("Semester not found"), HttpStatus.NOT_FOUND);
			}
		    semester=new SemesterEntity(semesterDto.getCode(), semesterDto.getNumberOfSem(), semesterDto.getSemesterStart(), semesterDto.getSemesterEnd());
			SchoolYearEntity schYear= new SchoolYearEntity(semesterDto.getSchYearDto().getCode(), semesterDto.getSchYearDto().getName(),
                                        semesterDto.getSchYearDto().getYearStart(), semesterDto.getSchYearDto().getYearEnd());
			SemesterEntity semesterr= semesterService.update(id, semester, schYear);
			SchoolYearDto schYearDto= new SchoolYearDto(semesterr.getSchoolYear().getCode(), semesterr.getSchoolYear().getName(), 
                   	semesterr.getSchoolYear().getYearStart(), semesterr.getSchoolYear().getYearEnd());
            SemesterDto semesterDtoo= new SemesterDto(semesterr.getCode(), semesterr.getNumberOfSem(), semesterr.getSemesterStart(), 
                           semesterr.getSemesterEnd(), schYearDto);
            return new ResponseEntity<SemesterDto>(semesterDtoo, HttpStatus.OK);	
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//brisanje polugodista ako ne postoje ocene ili odeljenja u tom polugodistu
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			SemesterEntity semester= semesterService.getById(id);
			if(semester==null) {
				return new ResponseEntity<RESTError>(new RESTError("Semester not found"), HttpStatus.NOT_FOUND);
			}
			semester= semesterService.delete(id);
			if(semester==null) {
				return new ResponseEntity<RESTError>(new RESTError("Semester is not been deleted, because there are gradings or classes releted to it."), HttpStatus.FORBIDDEN);
			}
			SchoolYearDto schYearDto=new SchoolYearDto(semester.getSchoolYear().getCode(), semester.getSchoolYear().getName(),
                                                	semester.getSchoolYear().getYearStart(), semester.getSchoolYear().getYearEnd());
	        SemesterDto semesterDto= new SemesterDto(semester.getCode(), semester.getNumberOfSem(), semester.getSemesterStart(),
                                                       semester.getSemesterEnd(), schYearDto);
            return new ResponseEntity<SemesterDto>(semesterDto, HttpStatus.OK);
        }catch(Exception e) {
	     return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
       }
	}
	
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}
