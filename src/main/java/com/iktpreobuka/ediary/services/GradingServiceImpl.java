package com.iktpreobuka.ediary.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ActivityEntity;
import com.iktpreobuka.ediary.entities.GradingEntity;
import com.iktpreobuka.ediary.entities.SemesterEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.TeacherSubjectClassEntity;
import com.iktpreobuka.ediary.entities.dto.MarkDto;
import com.iktpreobuka.ediary.repositories.GradingRepository;

@Service
public class GradingServiceImpl implements GradingService{

	@Autowired
	private GradingRepository gradingRepo;
	@Autowired
	private SemesterService semesterService;
	@Autowired
	private ActivityService activityService;
	
	@Override
	public List<GradingEntity> getAll(){
		return ( List<GradingEntity>) gradingRepo.findAll();
	}
	
	@Override
	public GradingEntity getById(Long id) {
		return gradingRepo.findById(id).get();
	}
	
	@Override
	public GradingEntity save(GradingEntity grading) {
		return gradingRepo.save(grading);
	}
	
	@Override
	public GradingEntity createGrading(GradingEntity newGrading){
		GradingEntity grading= new GradingEntity();
		grading.setDate(LocalDate.now());
		grading.setMark(newGrading.getMark());
		grading.setSemester(newGrading.getSemester());
		grading.setStudent(newGrading.getStudent());
		grading.setTeacherSubjectClass(newGrading.getTeacherSubjectClass());
		grading.setActivity(newGrading.getActivity());
		return save(grading);	
	}
	
	@Override
	public GradingEntity updateGrading(GradingEntity grading, GradingEntity newGrade) {
		grading.setActivity(newGrade.getActivity());
		grading.setDate(LocalDate.now());
		grading.setMark(newGrade.getMark());
		grading.setSemester(newGrade.getSemester());
		grading.setStudent(newGrade.getStudent());
		grading.setTeacherSubjectClass(newGrade.getTeacherSubjectClass());
		return save(grading);
	}
	
	@Override
	public GradingEntity delete(Long id) {
		GradingEntity grade= getById(id);
		gradingRepo.delete(grade);
		return grade;
	}
	
	@Override
	public List<MarkDto> getMarksBySubjectBySemester(StudentEntity student, TeacherSubjectClassEntity tsc, Integer sem){
		List<GradingEntity> gradings= gradingRepo.findByStudentAndTeacherSubjectClass(student, tsc);
		List<MarkDto> result= new ArrayList<>();
		for(GradingEntity grading: gradings) {
			if(grading.getSemester().getNumberOfSem().equals(sem)) {
			MarkDto mark= new MarkDto(grading.getMark(), grading.getActivity().getName());
			result.add(mark);
			}
		}
		return result;
	}
	
    @Override
	public GradingEntity finalGradeForStudentInSubject(StudentEntity student, TeacherSubjectClassEntity tsc) {
		SemesterEntity semester= semesterService.findInPresent();
		List<GradingEntity> gradings= gradingRepo.findByStudentAndTeacherSubjectClassAndSemester(student, tsc, semester);
		Integer sum=0;
		Integer n=0;
		for(GradingEntity grading: gradings) {
			if(grading.getActivity().getName().equalsIgnoreCase("zakljucna")) {
				return null;
			}
			Integer mark= grading.getMark();
			sum+=mark;
			n++;
		}
		Integer finalMark= (int) Math.round(sum/(double)n);
		ActivityEntity activity= activityService.findByName("zakljucna");
		GradingEntity grading= new GradingEntity(finalMark, activity, student, tsc, semester);
		grading.setDate(LocalDate.now());
		return save(grading);
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getMarksBySubjectAndSemester(Long studentId, Long tscId){
		String sql= "select g.mark " + "from GradingEntity g "+  
	                 "where g.student=:id and teacherSubjectClass=:idt ";
		
		Query query= em.createQuery(sql);
		query.setParameter("id", studentId);
		query.setParameter("idt", tscId);
		
		List<Integer> result= new ArrayList<>();
		result= query.getResultList();
		return result;
	}*/
	
	
}
