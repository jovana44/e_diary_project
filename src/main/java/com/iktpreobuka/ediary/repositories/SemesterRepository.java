package com.iktpreobuka.ediary.repositories;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.ediary.entities.SemesterEntity;

public interface SemesterRepository extends CrudRepository<SemesterEntity, Long> {


	@Query("SELECT s FROM SemesterEntity s WHERE s.semesterStart<= CURRENT_DATE AND s.semesterEnd>= CURRENT_DATE")
	SemesterEntity find();  //nadji trenutni semestar
}
