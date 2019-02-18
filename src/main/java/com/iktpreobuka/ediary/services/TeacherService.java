package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;


public interface TeacherService {

	public List<TeacherEntity> getAll();
	public TeacherEntity getById(Long id);
	public TeacherEntity save(TeacherEntity teacher);
	public TeacherEntity delete(Long id);
	public TeacherEntity update(Long id, TeacherEntity newTeacher, List<SubjectEntity> subjects);
	
	public TeacherEntity getByCode(String code);
	public TeacherEntity createTeacher(TeacherEntity teacher, List<SubjectEntity> subjects);
		
}
