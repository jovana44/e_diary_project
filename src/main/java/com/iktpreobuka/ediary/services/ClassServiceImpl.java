package com.iktpreobuka.ediary.services;

import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.entities.dto.SubjectDto;
import com.iktpreobuka.ediary.entities.dto.TeacherDto;
import com.iktpreobuka.ediary.entities.dto.TeacherSubjectDto;
import com.iktpreobuka.ediary.repositories.ClassRepository;

@Service
public class ClassServiceImpl implements ClassService{

	@Autowired
	private ClassRepository classRepo;
	@Autowired
	private TeacherSubjectClassService tscService;
	@Autowired
	private TeacherSubjectService tsService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SubjectService subjectService;
	
	@Override
	public List<ClassEntity> getAll(){
		return (List<ClassEntity>)classRepo.findAll();
	}
	
	@Override
	public ClassEntity getById(Long id) {
		return classRepo.findById(id).get();
	}
	
	@Override
	public ClassEntity getByCode(String code) {
		return classRepo.findByCode(code);
	}
	
	@Override
	public ClassEntity save(ClassEntity classs) {
		return classRepo.save(classs);
	}
	
	@Override
	public ClassEntity update(Long id, ClassEntity newClass) {
		ClassEntity classs= getById(id);
		classs.setCode(newClass.getCode());
		classs.setYear(newClass.getYear());
		classs.setNumberOfClass(newClass.getNumberOfClass());
		classs.setSchYear(newClass.getSchYear());
		return save(classs);
	}
	
	@Override
	public List<TeacherSubjectDto> addTeacherSubjects(Long id, List<TeacherSubjectDto> listDto) {
		ClassEntity classs= getById(id);
		List<TeacherSubjectClassEntity> tscList = classs.getTeacherSubjectClass();
		List<TeacherSubjectDto> tsListDto= new ArrayList<>();
		for(TeacherSubjectDto tsDto: listDto) {
			TeacherEntity teacher= teacherService.getByCode(tsDto.getTeacherDto().getCode());
			SubjectEntity subject= subjectService.getByName(tsDto.getSubjectDto().getName());
			TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
			TeacherSubjectClassEntity tsc= tscService.createTscIfNotFound(ts, classs, tsDto.getWeeklyHours());
			 if(!tscList.contains(tsc)) {
					tscList.add(tsc);
				}
		}
		classs.setTeacherSubjectClass(tscList);
		save(classs);
		for(TeacherSubjectClassEntity tscE: tscList) {
			TeacherDto teacherDto= new TeacherDto(tscE.getTeacherSubject().getTeacher().getCode(), 
				            	tscE.getTeacherSubject().getTeacher().getFirstName(), tscE.getTeacherSubject().getTeacher().getLastName());
		    SubjectDto subjectDto= new SubjectDto(tscE.getTeacherSubject().getSubject().getCode(), tscE.getTeacherSubject().getSubject().getName());
			TeacherSubjectDto tsDtoo= new TeacherSubjectDto(teacherDto, subjectDto, tscE.getWeeklyHours());
			tsListDto.add(tsDtoo);
		}
		return tsListDto;
	}
	
	@Override
	public TeacherSubjectDto deleteSubject(Long id, TeacherSubjectDto tsDto) {
		ClassEntity classs=getById(id);
		TeacherEntity teacher= teacherService.getByCode(tsDto.getTeacherDto().getCode());
		SubjectEntity subject= subjectService.getByName(tsDto.getSubjectDto().getName());
		TeacherSubjectEntity ts= tsService.findByTeacherAndSubject(teacher, subject);
		TeacherSubjectClassEntity tsc= tscService.findByTeacherSubjectAndClass(ts, classs);
		if(!(tsc.getGradings().isEmpty())) {
			return null;
		}
		tscService.delete(tsc.getId());
		return tsDto;
	}
	
	@Override
	public ClassEntity delete(Long id) {
		ClassEntity classs=getById(id);
		if(!(classs.getStudentsClass().isEmpty())) {
			return null;
		}
		for(TeacherSubjectClassEntity tsc: classs.getTeacherSubjectClass()){
			tscService.delete(tsc.getId());
		}
		classRepo.delete(classs);
		return classs;
	}
	
	@Override
	public ClassEntity findClassInPresentForStudent(StudentEntity student) {
		return classRepo.find(student);
	}
	
	@Override 
	public ClassEntity isPresent(ClassEntity classs) {
		if(LocalDate.now().isAfter(classs.getSchYear().getYearStart()) && LocalDate.now().isBefore(classs.getSchYear().getYearEnd())) {
               return classs;
            }
         return null;
	}
	
	
	
}
