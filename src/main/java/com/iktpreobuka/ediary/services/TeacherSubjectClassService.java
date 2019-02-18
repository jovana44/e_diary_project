package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;

public interface TeacherSubjectClassService {

	public TeacherSubjectClassEntity delete(Long id);
	
	public List<TeacherSubjectClassEntity> getAll();
	
	public TeacherSubjectClassEntity getById(Long id);
	
	public TeacherSubjectClassEntity createTscIfNotFound(TeacherSubjectEntity teacherSubject, ClassEntity classs, Integer weeklyHours);
	
	public TeacherSubjectClassEntity findByTeacherSubjectAndClass(TeacherSubjectEntity ts, ClassEntity classs);
	
	public TeacherSubjectClassEntity findByClassAndSubject(ClassEntity classs, Long idSubject);
}
