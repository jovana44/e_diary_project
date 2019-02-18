package com.iktpreobuka.ediary.services;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.StudentClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;

public interface StudentClassService {

	public StudentClassEntity findByStudentAndClass(StudentEntity student, ClassEntity classs);
	public StudentClassEntity createStudentClass(StudentEntity student, ClassEntity classs);
	public StudentClassEntity delete(StudentClassEntity sc);
}
