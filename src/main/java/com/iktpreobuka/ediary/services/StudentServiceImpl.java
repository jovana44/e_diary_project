package com.iktpreobuka.ediary.services;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.ediary.entities.ClassEntity;
import com.iktpreobuka.ediary.entities.GradingEntity;
import com.iktpreobuka.ediary.entities.ParentEntity;
import com.iktpreobuka.ediary.entities.StudentClassEntity;
import com.iktpreobuka.ediary.entities.StudentEntity;
import com.iktpreobuka.ediary.entities.UserEntity;
import com.iktpreobuka.ediary.repositories.StudentRepository;
import com.iktpreobuka.ediary.repositories.UserRepository;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private ParentService parentService;
	@Autowired
	private StudentClassService scService;
	@Autowired
	private ClassService classService;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<StudentEntity> getAll(){
		return (List<StudentEntity>)studentRepo.findAll();
	}
	
	@Override
	public StudentEntity getById(Long id){
		return studentRepo.findById(id).get();
		
	}
	
	@Override
	public StudentEntity getByCode(String code) {
		return studentRepo.findByCode(code);
	}
	
	@Override
	public StudentEntity save(StudentEntity student) {
		return studentRepo.save(student);
	}
	
	@Override
	public StudentEntity create(StudentEntity student, ParentEntity newParent, ClassEntity classs) {
		save(student);
		ParentEntity parent=parentService.createParentIfNotFound(newParent);
		student.setParent(parent);
		StudentClassEntity sc= scService.createStudentClass(student, classs);
		List<StudentClassEntity> scList= new ArrayList<>();
		scList.add(sc);
		student.setStudentClasses(scList);
		return save(student);
	}
	
	@Override
	public StudentEntity addClass(Long id, ClassEntity classs) {
		StudentEntity student= getById(id);
		List<StudentClassEntity> scList= student.getStudentClasses();
	    StudentClassEntity sc= scService.createStudentClass(student, classs);
	    scList.add(sc);
		student.setStudentClasses(scList);
		return save(student);
	}
	
	@Override
	public StudentEntity update(Long id, StudentEntity newStudent, ClassEntity newClass) {
		StudentEntity student= getById(id);
		student.setCode(newStudent.getCode());
		student.setFirstName(newStudent.getFirstName());
		student.setLastName(newStudent.getLastName());
		ParentEntity parent= student.getParent();
		parent= parentService.put(parent.getId(), newStudent.getParent());
		student.setParent(parent);
		
		List<StudentClassEntity> scList= student.getStudentClasses();
		ClassEntity classs= classService.findClassInPresentForStudent(student);
		StudentClassEntity scDel= scService.delete(scService.findByStudentAndClass(student, classs));
		scList.remove(scDel);
		
	    StudentClassEntity sc= scService.createStudentClass(student, newClass);
	    scList.add(sc);
		student.setStudentClasses(scList);
		return save(student);
	}
	
	@Override
	public List<StudentEntity> findStudentsByClass1(Long id){
		ClassEntity classs= classService.getById(id);
		List<StudentClassEntity> scList = classs.getStudentsClass();
		List<StudentEntity> result= new ArrayList<>();
		for(StudentClassEntity sc: scList) {
			StudentEntity student= sc.getStudentt();
			result.add(student);
		}
		return result;
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentEntity> findStudentsByClass(Long id){
	     String sql= "select s " + "from StudentEntity s "+ "left join fetch s.studentClasses sc left join fetch sc.classs c "
	                   + "where s.id=sc.studentt and c.id=:id";
	
	     Query query= em.createQuery(sql);
	     query.setParameter("id", id);
	
	     List<StudentEntity> result= new ArrayList<StudentEntity>();
	     result= query.getResultList();
	     return result;
    }
	
	@Override
	public StudentEntity deleteById(Long id) {
		StudentEntity student= getById(id);
		List<GradingEntity> gradings= student.getGradings();
		if(!(gradings.isEmpty())) {
			return null;
		}
		if(!(student.getStudentClasses().isEmpty())) {
			return null;
		}
		for(UserEntity user: student.getUsers()) {
			userRepo.delete(user);
		}
		studentRepo.delete(student);
		return student;
	}
}
