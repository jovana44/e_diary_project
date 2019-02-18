package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.dto.TeacherSubjectDto;

public interface ClassService {

	public List<ClassEntity> getAll();
	public ClassEntity getById(Long id);
	public ClassEntity save(ClassEntity classs);
	public ClassEntity delete(Long id);
	public ClassEntity update(Long id, ClassEntity newClass);
	public ClassEntity getByCode(String code);
	public List<TeacherSubjectDto> addTeacherSubjects(Long id, List<TeacherSubjectDto> listDto);
	public ClassEntity findClassInPresentForStudent(StudentEntity student);
	public ClassEntity isPresent(ClassEntity classs);
	public TeacherSubjectDto deleteSubject(Long id, TeacherSubjectDto tsDto);
}
