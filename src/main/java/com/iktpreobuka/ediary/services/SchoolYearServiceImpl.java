package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.SchoolYearEntity;
import com.iktpreobuka.ediary.repositories.SchoolYearRepository;

@Service
public class SchoolYearServiceImpl implements SchoolYearService{

	@Autowired
	private SchoolYearRepository schYearRepo;

	@Override
	public List<SchoolYearEntity> getAll(){
		return (List<SchoolYearEntity>) schYearRepo.findAll();
	}
	
	@Override
	public SchoolYearEntity getById(Long id) {
		return schYearRepo.findById(id).get();
	}
	
	@Override
	public SchoolYearEntity save(SchoolYearEntity schYear) {
		return schYearRepo.save(schYear);
	}
	
	@Override
	public SchoolYearEntity delete(Long id) {
		SchoolYearEntity schYear=getById(id);
		if(!(schYear.getSemesters().isEmpty())) {
			return null;
		}
		if(!(schYear.getClasses().isEmpty())) {
			return null;
		}
		schYearRepo.delete(schYear);
		return schYear;
	}
	
	@Override
	public SchoolYearEntity findByCode(String code) {
		return schYearRepo.findByCode(code);
	}
	
	@Override
	public SchoolYearEntity createSchYearIfNotFound(SchoolYearEntity schYear) {
		SchoolYearEntity schoolYear= findByCode(schYear.getCode());
		if(schoolYear==null) {
			return save(schYear);
		}
		return schoolYear;
		
	}
	
	@Override
	public SchoolYearEntity update(SchoolYearEntity schYear, SchoolYearEntity newSchYear) {
		schYear.setCode(newSchYear.getCode());
		schYear.setName(newSchYear.getName());
		schYear.setYearStart(newSchYear.getYearStart());
		schYear.setYearEnd(newSchYear.getYearEnd());
		return save(schYear);
	}
}
