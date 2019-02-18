package com.iktpreobuka.ediary.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Long> {

	ClassEntity findByCode(String code);
	
	@Query("SELECT c FROM ClassEntity c left join c.schYear sy left join c.studentsClass sc WHERE sy.yearStart<= CURRENT_DATE AND sy.yearEnd>= CURRENT_DATE AND sc.studentt=?1")
	ClassEntity find(StudentEntity student);  //nadji trenutno odeljenje za studenta
}

