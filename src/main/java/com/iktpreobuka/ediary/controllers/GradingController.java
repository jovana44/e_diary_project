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
import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.GradingEntity;
import com.iktpreobuka.ediary.entities.ParentEntity;
import com.iktpreobuka.ediary.entities.SemesterEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.entities.dto.ActivityDto;
import com.iktpreobuka.ediary.entities.dto.ClassDto;
import com.iktpreobuka.ediary.entities.dto.GradingDto;
import com.iktpreobuka.ediary.entities.dto.MarkDto;
import com.iktpreobuka.ediary.entities.dto.SemesterDto;
import com.iktpreobuka.ediary.entities.dto.StudentDto;
import com.iktpreobuka.ediary.entities.dto.StudentMarksDto;
import com.iktpreobuka.ediary.entities.dto.SubjectDto;
import com.iktpreobuka.ediary.entities.dto.SubjectMarksDto;
import com.iktpreobuka.ediary.entities.dto.TeacherDto;
import com.iktpreobuka.ediary.services.ActivityService;
import com.iktpreobuka.ediary.services.ClassService;
import com.iktpreobuka.ediary.services.EmailService;
import com.iktpreobuka.ediary.services.GradingService;
import com.iktpreobuka.ediary.services.ParentService;
import com.iktpreobuka.ediary.services.SemesterService;
import com.iktpreobuka.ediary.services.StudentService;
import com.iktpreobuka.ediary.services.SubjectService;
import com.iktpreobuka.ediary.services.TeacherService;
import com.iktpreobuka.ediary.services.TeacherSubjectClassService;
import com.iktpreobuka.ediary.services.TeacherSubjectService;

@RestController
@RequestMapping("/gradings")
public class GradingController {

	private final Logger logger =(Logger)LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GradingService gradingService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassService classService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private SemesterService semesterService;
	@Autowired
	private TeacherSubjectClassService tscService;
	@Autowired
	private TeacherSubjectService tsService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ParentService parentService;
	
	@RequestMapping(method=RequestMethod.GET)
	public  ResponseEntity<?> getAll(){
		try {
			List<GradingEntity> list= gradingService.getAll();
		
			List<GradingDto> gradingsDto= new ArrayList<>();
			for(GradingEntity grading: list) {
				TeacherDto teacherDto= new TeacherDto(grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getCode(),
                     	                           grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getFirstName(), 
                                                	grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getLastName());
                SubjectDto subjectDto= new SubjectDto( grading.getTeacherSubjectClass().getTeacherSubject().getSubject().getName());
                StudentDto studentDto= new StudentDto(grading.getStudent().getCode(), grading.getStudent().getFirstName(), grading.getStudent().getLastName(),
                                                       new ClassDto(grading.getTeacherSubjectClass().getClassss().getYear(), grading.getTeacherSubjectClass().getClassss().getNumberOfClass()));
                ActivityDto activityDto = new ActivityDto(grading.getActivity().getName());
                SemesterDto semesterDto= new SemesterDto(grading.getSemester().getNumberOfSem(), grading.getSemester().getSemesterStart(), grading.getSemester().getSemesterEnd());
                GradingDto gradeDto = new GradingDto(teacherDto, studentDto, subjectDto, activityDto, semesterDto, grading.getDate(), grading.getMark());
			    gradingsDto.add(gradeDto);
			}
			return new ResponseEntity<List<GradingDto>>(gradingsDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public  ResponseEntity<?> getById(@PathVariable Long id){
		try {
			GradingEntity grading= gradingService.getById(id);
			if(grading==null) {
				return new ResponseEntity<RESTError>(new RESTError("Grading not found."), HttpStatus.NOT_FOUND);
			}
			TeacherDto teacherDto= new TeacherDto(grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getCode(),
                                                 grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getFirstName(), 
                 	                             grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getLastName());
            SubjectDto subjectDto= new SubjectDto( grading.getTeacherSubjectClass().getTeacherSubject().getSubject().getName());
            StudentDto studentDto= new StudentDto(grading.getStudent().getCode(), grading.getStudent().getFirstName(), grading.getStudent().getLastName(),
                                                  new ClassDto(grading.getTeacherSubjectClass().getClassss().getYear(), grading.getTeacherSubjectClass().getClassss().getNumberOfClass()));
            ActivityDto activityDto = new ActivityDto(grading.getActivity().getName());
            SemesterDto semesterDto= new SemesterDto(grading.getSemester().getNumberOfSem(), grading.getSemester().getSemesterStart(), grading.getSemester().getSemesterEnd());
            GradingDto gradeDto = new GradingDto(teacherDto, studentDto, subjectDto, activityDto, semesterDto, grading.getDate(), grading.getMark());
			return new ResponseEntity<GradingDto>(gradeDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }

	
	@RequestMapping(method=RequestMethod.POST)
	public  ResponseEntity<?> createGrading(@Valid @RequestBody GradingDto gradingDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			TeacherEntity teacher= teacherService.getByCode(gradingDto.getTeacherDto().getCode());
			if(teacher==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher not found."), HttpStatus.NOT_FOUND);
			}
			SubjectEntity subject= subjectService.getByName(gradingDto.getSubjectDto().getName());
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("Subject not found."), HttpStatus.NOT_FOUND);
			}
			TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
			if(ts==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher dont have(teach) this subject."), HttpStatus.BAD_REQUEST);
			}
			StudentEntity student= studentService.getByCode(gradingDto.getStudentDto().getCode());
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("No student found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity classs= classService.findClassInPresentForStudent(student);
			TeacherSubjectClassEntity tsc= tscService.findByTeacherSubjectAndClass(ts, classs);
			if(tsc==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher cannot grading this student, because dont teach this student."), HttpStatus.BAD_REQUEST);
			}
			ActivityEntity activity= activityService.findByName(gradingDto.getActivityDto().getName());
			if(activity==null) {
				return new ResponseEntity<RESTError>(new RESTError("No activity found."), HttpStatus.NOT_FOUND);
			}
			SemesterEntity semester= semesterService.findInPresent();
			if(semester ==null) {
				return new ResponseEntity<RESTError>(new RESTError("No semester found."), HttpStatus.NOT_FOUND);
			}
			GradingEntity grading= new GradingEntity(gradingDto.getMark(), activity, student, tsc, semester);
			grading = gradingService.createGrading(grading);
			emailService.sendTemplateMessage(grading, "Your child got a new grade!");
			TeacherDto teacherDto= new TeacherDto(grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getCode(),
				                             	grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getFirstName(), 
				                             	grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getLastName());
			SubjectDto subjectDto= new SubjectDto( grading.getTeacherSubjectClass().getTeacherSubject().getSubject().getName());
			StudentDto studentDto= new StudentDto(grading.getStudent().getCode(), grading.getStudent().getFirstName(), grading.getStudent().getLastName(),
					                              new ClassDto(classs.getYear(), classs.getNumberOfClass()));
			ActivityDto activityDto = new ActivityDto(grading.getActivity().getName());
			SemesterDto semesterDto= new SemesterDto(grading.getSemester().getNumberOfSem(), grading.getSemester().getSemesterStart(), grading.getSemester().getSemesterEnd());
			GradingDto gradeDto = new GradingDto(teacherDto, studentDto, subjectDto, activityDto, semesterDto, grading.getDate(), grading.getMark());
			return new ResponseEntity<GradingDto>(gradeDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	
	//promena ocene
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public  ResponseEntity<?> updateGrading(@PathVariable Long id,@Valid @RequestBody GradingDto gradingDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			GradingEntity grad= gradingService.getById(id);
			if(grad==null) {
				return new ResponseEntity<RESTError>(new RESTError("Grading not found."), HttpStatus.NOT_FOUND);
			}
			TeacherEntity teacher= teacherService.getByCode(gradingDto.getTeacherDto().getCode());
			if(teacher==null) {
				return new ResponseEntity<RESTError>(new RESTError("No teacher found."), HttpStatus.NOT_FOUND);
			}
			SubjectEntity subject= subjectService.getByName(gradingDto.getSubjectDto().getName());
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("No subject found."), HttpStatus.NOT_FOUND);
			}
			TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
			if(ts==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher dont have this subject."), HttpStatus.BAD_REQUEST);
			}
			StudentEntity student= studentService.getByCode(gradingDto.getStudentDto().getCode());
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("No student found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity classs= classService.findClassInPresentForStudent(student);
			TeacherSubjectClassEntity tsc= tscService.findByTeacherSubjectAndClass(ts, classs);
			if(tsc==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher cannot grading this student."), HttpStatus.BAD_REQUEST);
			}
			ActivityEntity activity= activityService.findByName(gradingDto.getActivityDto().getName());
			if(activity==null) {
				return new ResponseEntity<RESTError>(new RESTError("No activity found."), HttpStatus.NOT_FOUND);
			}
			SemesterEntity semester= semesterService.findInPresent();
			if(semester ==null) {
				return new ResponseEntity<RESTError>(new RESTError("No semester found."), HttpStatus.NOT_FOUND);
			}
			GradingEntity newGrade= new GradingEntity(gradingDto.getMark(), activity, student, tsc, semester);
			GradingEntity grading = gradingService.updateGrading(gradingService.getById(id), newGrade);
			TeacherDto teacherDto= new TeacherDto(grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getCode(),
                                            	grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getFirstName(), 
                                              	grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getLastName());
            SubjectDto subjectDto= new SubjectDto( grading.getTeacherSubjectClass().getTeacherSubject().getSubject().getName());
            StudentDto studentDto= new StudentDto(grading.getStudent().getCode(), grading.getStudent().getFirstName(), grading.getStudent().getLastName(),
                                                        new ClassDto(classs.getYear(), classs.getNumberOfClass()));
            ActivityDto activityDto = new ActivityDto(grading.getActivity().getName());
            SemesterDto semesterDto= new SemesterDto(grading.getSemester().getNumberOfSem(), grading.getSemester().getSemesterStart(), grading.getSemester().getSemesterEnd());
            GradingDto gradeDto = new GradingDto(teacherDto, studentDto, subjectDto, activityDto, semesterDto, grading.getDate(), grading.getMark());
			return new ResponseEntity<GradingDto>(gradeDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public  ResponseEntity<?> deleteGrading(@PathVariable Long id){
		try {
			GradingEntity grad= gradingService.getById(id);
			if(grad==null) {
				return new ResponseEntity<RESTError>(new RESTError("Grading not found."), HttpStatus.NOT_FOUND);
			}
			GradingEntity grading= gradingService.delete(id);
			TeacherDto teacherDto= new TeacherDto(grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getCode(),
                	                             grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getFirstName(), 
                  	                             grading.getTeacherSubjectClass().getTeacherSubject().getTeacher().getLastName());
            SubjectDto subjectDto= new SubjectDto( grading.getTeacherSubjectClass().getTeacherSubject().getSubject().getName());
            StudentDto studentDto= new StudentDto(grading.getStudent().getCode(), grading.getStudent().getFirstName(), grading.getStudent().getLastName(),
                                                  new ClassDto(grading.getTeacherSubjectClass().getClassss().getYear(), grading.getTeacherSubjectClass().getClassss().getNumberOfClass()));
            ActivityDto activityDto = new ActivityDto(grading.getActivity().getName());
            SemesterDto semesterDto= new SemesterDto(grading.getSemester().getNumberOfSem(), grading.getSemester().getSemesterStart(), grading.getSemester().getSemesterEnd());
            GradingDto gradeDto = new GradingDto(teacherDto, studentDto, subjectDto, activityDto, semesterDto, grading.getDate(), grading.getMark());
              return new ResponseEntity<GradingDto>(gradeDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }

	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	//metoda za nastavnika, pregled ocena iz predemta koji predaje za jedno odeljenje
	@RequestMapping(method=RequestMethod.GET, value="/{id}/teacher/{idSubject}/subject/{idClass}/class")
	public  ResponseEntity<?> getMarksForTeacher(@PathVariable Long id, @PathVariable Long idSubject, @PathVariable Long idClass){
		try {
			TeacherEntity teacher= teacherService.getById(id);
			if(teacher==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher not found."), HttpStatus.NOT_FOUND);
			}
			SubjectEntity subject= subjectService.getById(idSubject);
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("Subject not found."), HttpStatus.NOT_FOUND);
			}
			TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
			if(ts==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher dont have this subject."), HttpStatus.BAD_REQUEST);
			}
			ClassEntity classs= classService.getById(idClass);
			TeacherSubjectClassEntity tsc= tscService.findByTeacherSubjectAndClass(ts, classs);
			if(tsc==null) {
				return new ResponseEntity<RESTError>(new RESTError("Teacher cannot see marks for this student, because he dont teach him."), HttpStatus.BAD_REQUEST);
			}	
			List<StudentEntity> students= studentService.findStudentsByClass(idClass);
			List<StudentMarksDto> studentList= new ArrayList<>();
			List<MarkDto> marksFirst= new ArrayList<>();
			List<MarkDto> marksSecond = new ArrayList<>();
			for(StudentEntity student: students) {
				StudentDto studentDto = new StudentDto(student.getCode(), student.getFirstName(), student.getLastName());
				marksFirst= gradingService.getMarksBySubjectBySemester(student, tsc, 1);
				marksSecond= gradingService.getMarksBySubjectBySemester(student, tsc, 2);
				StudentMarksDto smDto= new StudentMarksDto(studentDto, marksFirst, marksSecond);
				studentList.add(smDto);
			}
            return new ResponseEntity<List<StudentMarksDto>>(studentList, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	
	//metoda za ucenike, pregled ocena iz svih predmeta
	@RequestMapping(method=RequestMethod.GET, value="/{id}/student/{idClass}/class")
	public  ResponseEntity<?> getMarksForStudentForAllSubjects(@PathVariable Long id, @PathVariable Long idClass){
		try {
			StudentEntity student= studentService.getById(id);
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity classs= classService.getById(idClass);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class not found."), HttpStatus.NOT_FOUND);
			}
			if(!(studentService.findStudentsByClass(idClass).contains(student))) {
				return new ResponseEntity<RESTError>(new RESTError("Student does not belong to this class."), HttpStatus.BAD_REQUEST);
			}
			List<SubjectMarksDto> list= new ArrayList<>();
			List<MarkDto> marksFirstSem= new ArrayList<>();
			List<MarkDto> marksSecondSem= new ArrayList<>();
			for(TeacherSubjectClassEntity tsc: classs.getTeacherSubjectClass()) {
				SubjectDto subjectDto= new SubjectDto(tsc.getTeacherSubject().getSubject().getName());
				marksFirstSem= gradingService.getMarksBySubjectBySemester(student, tsc, 1);
				marksSecondSem=gradingService.getMarksBySubjectBySemester(student, tsc, 2);
				SubjectMarksDto subjectMarksDto= new SubjectMarksDto(subjectDto, marksFirstSem, marksSecondSem);
				list.add(subjectMarksDto);
			}
            return new ResponseEntity<List<SubjectMarksDto>>(list, HttpStatus.OK);
          }catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	
	//metoda za ucenike, pregled ocena iz odredjenog predmeta
	@RequestMapping(method=RequestMethod.GET, value="/{id}/student/{idClass}/class/{idSubject}/subject")
	public  ResponseEntity<?> getMarksForStudentForOneSubject(@PathVariable Long id, @PathVariable Long idClass, @PathVariable Long idSubject){
		try {
			StudentEntity student= studentService.getById(id);
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
			ClassEntity classs= classService.getById(idClass);
			if(classs==null) {
				return new ResponseEntity<RESTError>(new RESTError("Class not found."), HttpStatus.NOT_FOUND);
			}
			if(!(studentService.findStudentsByClass(idClass).contains(student))) {
				return new ResponseEntity<RESTError>(new RESTError("Student does not belong to this class."), HttpStatus.BAD_REQUEST);
			}
			SubjectEntity subject= subjectService.getById(idSubject);
			if(subject==null) {
				return new ResponseEntity<RESTError>(new RESTError("Subject not found."), HttpStatus.NOT_FOUND);
			}
			TeacherSubjectClassEntity tsc= tscService.findByClassAndSubject(classs, idSubject);
			if(tsc==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student dont have this subject."), HttpStatus.BAD_REQUEST);
			}
			SubjectDto subjectDto= new SubjectDto(subjectService.getById(idSubject).getName());
			List<MarkDto> marksFirstSem= gradingService.getMarksBySubjectBySemester(student, tsc, 1);
			List<MarkDto> marksSecondSem= gradingService.getMarksBySubjectBySemester(student, tsc, 2);
			SubjectMarksDto subjectMarksDto= new SubjectMarksDto(subjectDto, marksFirstSem, marksSecondSem);
            return new ResponseEntity<SubjectMarksDto>(subjectMarksDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
	
	//metoda za roditelje, pregled svih ocena deteta
	@RequestMapping(method=RequestMethod.GET, value="/{id}/parent/{idClass}/class/{idStudent}/student")
	public  ResponseEntity<?> getMarksForParent(@PathVariable Long id, @PathVariable Long idClass, @PathVariable Long idStudent){
		try {
			ParentEntity parent= parentService.getById(id);
			if(parent==null) {
				return new ResponseEntity<RESTError>(new RESTError("Parent not found."), HttpStatus.NOT_FOUND);
			}
			StudentEntity student= studentService.getById(idStudent);
			if(student==null) {
				return new ResponseEntity<RESTError>(new RESTError("Student not found."), HttpStatus.NOT_FOUND);
			}
			if(student.getParent()!=parent) {
				return new ResponseEntity<RESTError>(new RESTError("This is not parent of this student."), HttpStatus.BAD_REQUEST);
			}
			ClassEntity classs= classService.getById(idClass);
			if(!(studentService.findStudentsByClass(idClass).contains(student))) {
				return new ResponseEntity<RESTError>(new RESTError("Student does not belong to this class."), HttpStatus.BAD_REQUEST);
			}
			List<SubjectMarksDto> list= new ArrayList<>();
			List<MarkDto> marksFirstSem= new ArrayList<>();
			List<MarkDto> marksSecondSem= new ArrayList<>();
			for(TeacherSubjectClassEntity tsc: classs.getTeacherSubjectClass()) {
				SubjectDto subjectDto= new SubjectDto(tsc.getTeacherSubject().getSubject().getName());
				marksFirstSem= gradingService.getMarksBySubjectBySemester(student, tsc, 1);
				marksSecondSem= gradingService.getMarksBySubjectBySemester(student, tsc, 2);
				SubjectMarksDto subjectMarksDto= new SubjectMarksDto(subjectDto, marksFirstSem, marksSecondSem);
				list.add(subjectMarksDto);
			}
            return new ResponseEntity<List<SubjectMarksDto>>(list, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	   }
}
