package com.iktpreobuka.ediary.services;

import java.util.ArrayList;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.entities.UserEntity;
import com.iktpreobuka.ediary.repositories.TeacherRepository;
import com.iktpreobuka.ediary.repositories.UserRepository;

@Service
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private TeacherRepository teacherRepo;
	@Autowired
	private TeacherSubjectService teacherSubjectService;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<TeacherEntity> getAll(){
		return (List<TeacherEntity>) teacherRepo.findAll();
	}
	
	@Override
	public TeacherEntity getById(Long id) {
		return teacherRepo.findById(id).get();
	}
	
	@Override
	public TeacherEntity getByCode(String code) {
		return teacherRepo.getByCode(code);
	}
	
	@Override
	public TeacherEntity save(TeacherEntity teacher) {
		return teacherRepo.save(teacher);
	}
	
	@Override
	public TeacherEntity createTeacher(TeacherEntity teacher, List<SubjectEntity> subjects) {
		List<TeacherSubjectEntity> teacherSubjects=new ArrayList<>();
		save(teacher);
		
		for(SubjectEntity subject: subjects) {
	    	TeacherSubjectEntity teacherSubject= teacherSubjectService.createIfNotFound(teacher, subject);
	    	if(!teacherSubjects.contains(teacherSubject)) {
	    		teacherSubjects.add(teacherSubject);
	       	}
		}
		teacher.setTeachersSubjects(teacherSubjects);
		return save(teacher);
	}
	
	@Override
	public TeacherEntity update(Long id, TeacherEntity newTeacher, List<SubjectEntity> subjects) {
		TeacherEntity teacher=getById(id);
		teacher.setCode(newTeacher.getCode());
		teacher.setFirstName(newTeacher.getFirstName());
		teacher.setLastName(newTeacher.getLastName());
		
		//izbrisi predemet iz stare liste ako nastavnik nije predavao bar jednom odeljenju taj predmet
		List<TeacherSubjectEntity> teacherSubjects=teacher.getTeachersSubjects();
		for(TeacherSubjectEntity ts: teacher.getTeachersSubjects()) {
			if(ts.getTeachersSubjectsClasses().isEmpty()) {
				teacherSubjectService.deleteTS(ts);
				teacherSubjects.remove(ts);
			}
		}
		
		//napravi neke nove predmete ako vec ne postoje u staroj listi
		for(SubjectEntity subject: subjects) {
			TeacherSubjectEntity teacherSubject= teacherSubjectService.createIfNotFound(teacher, subject);
			if(!(teacherSubjects.contains(teacherSubject))) {
	    		teacherSubjects.add(teacherSubject);
			}
		}
		
		teacher.setTeachersSubjects(teacherSubjects);
		return teacherRepo.save(teacher);
	}
	
	@Override
	public TeacherEntity delete(Long id) {
		TeacherEntity teacher=getById(id);
		for(TeacherSubjectEntity teacherSubject: teacher.getTeachersSubjects()) {
			if(!( teacherSubject.getTeachersSubjectsClasses().isEmpty())) {
				return null;
			}
		}	
		if(teacher.getUsers().size()>1) {
			return null;
		}
		for(TeacherSubjectEntity teacherSubject: teacher.getTeachersSubjects()) {
			teacherSubjectService.deleteTS(teacherSubject);
	    }
		for(UserEntity user:teacher.getUsers()) {
			userRepo.delete(user);
		}
		teacherRepo.delete(teacher);
		return teacher;
	}
	
	
	
	

}
