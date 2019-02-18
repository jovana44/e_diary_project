package com.iktpreobuka.ediary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.StudentClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.repositories.StudentClassRepository;

@Service
public class StudentClassServiceImpl implements StudentClassService{
	
	@Autowired
	private StudentClassRepository scRepo;
	
	@Override
	public StudentClassEntity findByStudentAndClass(StudentEntity student, ClassEntity classs) {
		return scRepo.findByStudenttAndClasss(student, classs);
	}
	
	@Override
	public StudentClassEntity createStudentClass(StudentEntity student, ClassEntity classs) {
		StudentClassEntity sc= new StudentClassEntity();
		sc.setClasss(classs);
		sc.setStudentt(student);
		return scRepo.save(sc);
	}

	
	@Override
	public StudentClassEntity delete(StudentClassEntity sc) {
		scRepo.delete(sc);
		return sc;
	}

}
