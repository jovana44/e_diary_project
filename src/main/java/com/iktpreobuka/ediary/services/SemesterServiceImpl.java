package com.iktpreobuka.ediary.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iktpreobuka.ediary.entities.SchoolYearEntity;
import com.iktpreobuka.ediary.entities.SemesterEntity;
import com.iktpreobuka.ediary.repositories.SemesterRepository;

@Service
public class SemesterServiceImpl implements SemesterService{

	@Autowired
	private SemesterRepository semesterRepo;
	@Autowired
	private SchoolYearService schYearService;

	
	@Override
	public List<SemesterEntity> getAll(){
		return (List<SemesterEntity>) semesterRepo.findAll();
	}
	
	@Override
	public SemesterEntity getById(Long id) {
		return semesterRepo.findById(id).get();
	}
	
	@Override
	public SemesterEntity save(SemesterEntity semester) {
		return semesterRepo.save(semester);
	}
	
	@Override
	public SemesterEntity delete(Long id) {
		SemesterEntity sem= getById(id);
		if(!(sem.getGradings().isEmpty())) {
			return null;
		}
		if(!(sem.getSchoolYear().getClasses().isEmpty())) {
			return null;
		}
		if(sem.getSchoolYear().getSemesters().size()==2){
			semesterRepo.delete(sem);
			return sem;
		}
		schYearService.delete(sem.getSchoolYear().getId());
		semesterRepo.delete(sem);
		return sem;
	}
	
	@Override
	public SemesterEntity createSemester(SemesterEntity semester, SchoolYearEntity schYear) {
		SchoolYearEntity schoolYear= schYearService.createSchYearIfNotFound(schYear);
		semester.setSchoolYear(schoolYear);
		return save(semester);
	}
	
	@Override
	public SemesterEntity update(Long id, SemesterEntity newSemester, SchoolYearEntity schYear) {
		SemesterEntity semester= getById(id);
		SchoolYearEntity schoolYear= semester.getSchoolYear();
		schoolYear=	 schYearService.update(schoolYear, schYear);
		semester.setCode(newSemester.getCode());
		semester.setNumberOfSem(newSemester.getNumberOfSem());
		semester.setSemesterStart(newSemester.getSemesterStart());
		semester.setSemesterEnd(newSemester.getSemesterEnd());
		semester.setSchoolYear(schoolYear);
		return save(semester);
	}
	
	@Override
	public SemesterEntity findInPresent() {
		return semesterRepo.find();
	}
	
	
	
}
