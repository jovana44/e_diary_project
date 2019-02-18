package com.iktpreobuka.ediary.services;

import java.util.List;

import com.iktpreobuka.ediary.entities.SchoolYearEntity;
import com.iktpreobuka.ediary.entities.SemesterEntity;

public interface SemesterService {

	public List<SemesterEntity> getAll();
	public SemesterEntity getById(Long id);
	public SemesterEntity save(SemesterEntity semester);
	public SemesterEntity delete(Long id);
	public SemesterEntity update(Long id, SemesterEntity newSemester, SchoolYearEntity schYear);
	public SemesterEntity createSemester(SemesterEntity semester, SchoolYearEntity schYear);

	
	public SemesterEntity findInPresent();
}
