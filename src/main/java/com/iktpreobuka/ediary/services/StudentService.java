package com.iktpreobuka.ediary.services;

import java.util.List;
import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.ParentEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;


public interface StudentService {

	public List<StudentEntity> getAll();
	public StudentEntity getById(Long id);
	public StudentEntity save(StudentEntity student);
	public StudentEntity deleteById(Long id);
	public StudentEntity update(Long id, StudentEntity newStudent, ClassEntity classs);
	public StudentEntity create(StudentEntity student, ParentEntity newParent, ClassEntity classs);
	public StudentEntity getByCode(String code);
	public StudentEntity addClass(Long id, ClassEntity classs);
	public List<StudentEntity> findStudentsByClass(Long id);
	public List<StudentEntity> findStudentsByClass1(Long id);
	
	
}
