package com.iktpreobuka.ediary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.StudentClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;

public interface StudentClassRepository extends CrudRepository<StudentClassEntity, Long> {

	StudentClassEntity findByStudenttAndClasss(StudentEntity student, ClassEntity classs);
}
