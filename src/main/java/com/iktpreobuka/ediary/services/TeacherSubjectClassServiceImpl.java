package com.iktpreobuka.ediary.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectEntity;
import com.iktpreobuka.ediary.repositories.TeacherSubjectClassRepository;

@Service
public class TeacherSubjectClassServiceImpl implements TeacherSubjectClassService{

	@Autowired
	private TeacherSubjectClassRepository tscRepo;
	
	@Override
	public List<TeacherSubjectClassEntity> getAll(){
		return (List<TeacherSubjectClassEntity>) tscRepo.findAll();
	}
	
	@Override
	public TeacherSubjectClassEntity getById(Long id) {
		return tscRepo.findById(id).get();
	}
	
	@Override
	public TeacherSubjectClassEntity delete(Long id) {
		TeacherSubjectClassEntity tsc=getById(id);
		tscRepo.delete(tsc);
		return tsc;
	}
	
	@Override
	public TeacherSubjectClassEntity createTscIfNotFound( TeacherSubjectEntity teacherSubject, ClassEntity classs, Integer weeklyHours) {
		TeacherSubjectClassEntity tsc= findByTeacherSubjectAndClass(teacherSubject, classs);
        if(tsc==null) {
          tsc=new TeacherSubjectClassEntity();
		  tsc.setClassss(classs);
		  tsc.setTeacherSubject(teacherSubject);
		  tsc.setWeeklyHours(weeklyHours);
		  return tscRepo.save(tsc);
        }
		return tscRepo.save(tsc);
	}
	
	@Override
	public TeacherSubjectClassEntity findByTeacherSubjectAndClass(TeacherSubjectEntity ts, ClassEntity classs) {
		return tscRepo.findByTeacherSubjectAndClassss(ts, classs);
	}
	
	@Override
	public TeacherSubjectClassEntity findByClassAndSubject(ClassEntity classs, Long idSubject) {
		return tscRepo.findByClassss(idSubject, classs);
	}
	
	

	
	
}
