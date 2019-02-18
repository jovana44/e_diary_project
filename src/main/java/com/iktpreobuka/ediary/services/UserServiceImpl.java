package com.iktpreobuka.ediary.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iktpreobuka.ediary.entities.PersonEntity;
import com.iktpreobuka.ediary.entities.URoleEntity;
import com.iktpreobuka.ediary.entities.UserEntity;
import com.iktpreobuka.ediary.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private URoleService uRoleService;
	@Autowired
	private ParentService parentService;
	@Autowired
	private AdminService adminService;

	
	@Override
	public List<UserEntity> getAll(){
		return (List<UserEntity>) userRepo.findAll();
	}
	
	@Override
	public UserEntity getById(Long id) {
		return userRepo.findById(id).get();
	}
	
	@Override
	public UserEntity save(UserEntity user) {
		if(userRepo.findByUserRoleAndPerson(user.getUserRole(), user.getPerson())!=null) {
			return null;
		}
		return userRepo.save(user);
	}
	
	@Override
	public UserEntity createUser(UserEntity user, PersonEntity person) {
		//&& userRepo.findByUserRoleAndPerson(uRoleService.findByName("teacher"), person)==null
		if(person.getUsers().isEmpty()) {
			if(teacherService.getByCode(person.getCode())!=null) {
				user.setUserRole(uRoleService.findByName("teacher"));
				user.setPerson(person);
				
			}
		
			if(studentService.getByCode(person.getCode())!=null) {
				
				user.setUserRole(uRoleService.findByName("student"));
				user.setPerson(person);
				
			}
		
			if(parentService.findByCode(person.getCode())!=null ) {
				user.setUserRole(uRoleService.findByName("parent"));
				user.setPerson(person);
				
			}
		
			if(adminService.findByCode(person.getCode())!=null) {
				user.setUserRole(uRoleService.findByName("admin"));
				user.setPerson(person);
				
			}
		return userRepo.save(user);
		}
		return null;
	}
	
	@Override
	public UserEntity update(Long id, UserEntity newUser) {
		UserEntity user= userRepo.findById(id).get();
		user.setUsername(newUser.getUsername());
		user.setPassword(newUser.getPassword());
		return userRepo.save(user);
	}
	
	@Override
	public UserEntity updateRole(Long id, URoleEntity role) {
		UserEntity user= userRepo.findById(id).get();
		if(userRepo.findByUserRoleAndPerson(role, user.getPerson())!=null) {
			return null;
		}
		user.setUserRole(role);
		return userRepo.save(user);
	}
	
		
	@Override
	public UserEntity delete(Long id) {
		UserEntity user=userRepo.findById(id).get();
		userRepo.delete(user);
		return user;
	}
}
