package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.GradingEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.dto.MarkDto;

public interface GradingService {

	public List<GradingEntity> getAll();
	public GradingEntity getById(Long id);
	public GradingEntity delete(Long id);
	public GradingEntity save(GradingEntity grading);
	
	public GradingEntity createGrading(GradingEntity newGrading);
	public GradingEntity updateGrading(GradingEntity grading, GradingEntity newGrade);
	

	//public List<Integer> getMarksBySubjectAndSemester(Long studentId, Long tscId);
	
	public List<MarkDto> getMarksBySubjectBySemester(StudentEntity student, TeacherSubjectClassEntity tsc, Integer sem);
	
	public GradingEntity finalGradeForStudentInSubject(StudentEntity student, TeacherSubjectClassEntity tsc);
}
