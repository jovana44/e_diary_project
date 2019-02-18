package com.iktpreobuka.ediary.repositories;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.GradingEntity;
import com.iktpreobuka.ediary.entities.SemesterEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;

public interface GradingRepository extends CrudRepository<GradingEntity, Long> {

	List<GradingEntity> findByStudentAndTeacherSubjectClass(StudentEntity student, TeacherSubjectClassEntity tsc);
	List<GradingEntity> findByStudentAndTeacherSubjectClassAndSemester(StudentEntity student,TeacherSubjectClassEntity tsc, SemesterEntity semester );
}
