package com.iktpreobuka.ediary.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.repositories.TeacherSubjectRepository;

@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService{

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Override
	public TeacherSubjectEntity createIfNotFound(TeacherEntity teacher, SubjectEntity subject) {
		TeacherSubjectEntity teacherSubject= teacherSubjectRepo.findByTeacherAndSubject(teacher, subject);
		if(teacherSubject==null) {
		  teacherSubject= new TeacherSubjectEntity();
		  teacherSubject.setTeacher(teacher);
		  teacherSubject.setSubject(subject);
		  return teacherSubjectRepo.save(teacherSubject);
		}
		return teacherSubject;
	}
	
	
	@Override
	public TeacherSubjectEntity deleteTS(TeacherSubjectEntity teacherSubject) {
		if(teacherSubject.getTeachersSubjectsClasses().isEmpty()) {
		teacherSubjectRepo.delete(teacherSubject);
		return teacherSubject;
		}
		return null;
	}
	
	@Override
	public TeacherSubjectEntity findByTeacherAndSubject(TeacherEntity teacher, SubjectEntity subject) {
		return teacherSubjectRepo.findByTeacherAndSubject(teacher, subject);
	}
}
