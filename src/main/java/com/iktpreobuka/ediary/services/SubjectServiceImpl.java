package com.iktpreobuka.ediary.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.SubjectEntity;
import com.iktpreobuka.ediary.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	private SubjectRepository subjectRepo;
	
	@Override
	public List<SubjectEntity> getAll(){
		return (List<SubjectEntity>) subjectRepo.findAll();
	}
	
	@Override
	public SubjectEntity getById(Long id) {
		return subjectRepo.findById(id).get();
	}
	
	@Override
	public SubjectEntity getByName(String name) {
		return subjectRepo.findByName(name);
	}
	
	@Override
	public SubjectEntity save(SubjectEntity subject) {
		return subjectRepo.save(subject);
	}
	
	@Override
	public SubjectEntity createIfNotFound(String code, String name) {
		SubjectEntity subject=subjectRepo.findByCode(code);
		if(subject==null) {
			subject =new SubjectEntity();
			subject.setCode(code);
			subject.setName(name);
			return save(subject);
		}
		return subject;
	}
	
	@Override
	public SubjectEntity delete(Long id) {
		SubjectEntity subject= getById(id);
		if(!(subject.getTeachersSubjects().isEmpty())) {
			return null;
		}
		subjectRepo.delete(subject);
		return subject;
	}
	
	@Override
	public SubjectEntity update(Long id, SubjectEntity newSubject) {
		SubjectEntity subject= getById(id);
		subject.setCode(newSubject.getCode());
		subject.setName(newSubject.getName());
		return save(subject);
	}
	
	
}
