package com.iktpreobuka.ediary.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;

public interface TeacherSubjectClassRepository extends CrudRepository<TeacherSubjectClassEntity, Long> {

	TeacherSubjectClassEntity findByTeacherSubjectAndClassss(TeacherSubjectEntity teacherSubject, ClassEntity classs);
	List<TeacherSubjectClassEntity> findByClassss(ClassEntity classs);
	
	@Query("SELECT tsc FROM TeacherSubjectClassEntity tsc left join tsc.teacherSubject ts left join ts.subject s WHERE s.id=?1 ")
	TeacherSubjectClassEntity findByClassss( Long id, ClassEntity classss);
}
