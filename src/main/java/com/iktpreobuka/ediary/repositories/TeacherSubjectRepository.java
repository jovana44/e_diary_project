package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.entities.TeacherEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;

public interface TeacherSubjectRepository extends CrudRepository<TeacherSubjectEntity, Long> {

	
	TeacherSubjectEntity findByTeacherAndSubject(TeacherEntity teacher, SubjectEntity subject);
}
