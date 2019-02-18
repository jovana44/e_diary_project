package com.iktpreobuka.ediary.services;

import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;

public interface TeacherSubjectService {
	
	public TeacherSubjectEntity deleteTS(TeacherSubjectEntity teacherSubject);
	
	public TeacherSubjectEntity findByTeacherAndSubject(TeacherEntity teacher, SubjectEntity subject);
	
	public TeacherSubjectEntity createIfNotFound(TeacherEntity teacher, SubjectEntity subject);
}
