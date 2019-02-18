package com.iktpreobuka.ediary.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.ediary.controllers.util.RESTError;
import com.iktpreobuka.ediary.entities.AdminEntity;
import com.iktpreobuka.ediary.entities.dto.AdminDto;
import com.iktpreobuka.ediary.services.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		try {
			List<AdminEntity> admins= adminService.findAll();
			List<AdminDto> adminsDto= new ArrayList<>();
			for(AdminEntity admin: admins) {
				AdminDto adminDto= new AdminDto(admin.getCode(), admin.getFirstName(), admin.getLastName());
				adminsDto.add(adminDto);
			}
			return new ResponseEntity<List<AdminDto>>(adminsDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value= "/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			AdminEntity admin= adminService.getById(id);
			if(admin==null) {
				return new ResponseEntity<RESTError>(new RESTError("Admin not found."), HttpStatus.NOT_FOUND);
			}
			AdminDto adminDto= new AdminDto(admin.getCode(), admin.getFirstName(), admin.getLastName());
			return new ResponseEntity<AdminDto>(adminDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminDto adminDto,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			AdminEntity admin= adminService.save(new AdminEntity(adminDto.getCode(), adminDto.getFirstName(), adminDto.getLastName()));
			AdminDto adminDtoo= new AdminDto(admin.getCode(), admin.getFirstName(), admin.getLastName());
			return new ResponseEntity<AdminDto>(adminDtoo, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, value= "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AdminDto newAdminDto,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			AdminEntity adm= adminService.getById(id);
			if(adm==null) {
				return new ResponseEntity<RESTError>(new RESTError("Admin not found."), HttpStatus.NOT_FOUND);
			}
			AdminEntity admin= adminService.update(id, new AdminEntity(newAdminDto.getCode(), newAdminDto.getFirstName(), newAdminDto.getLastName()));
			AdminDto adminDto= new AdminDto(admin.getCode(), admin.getFirstName(), admin.getLastName());
			return new ResponseEntity<AdminDto>(adminDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value= "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			AdminEntity adm= adminService.getById(id);
			if(adm==null) {
				return new ResponseEntity<RESTError>(new RESTError("Admin not found."), HttpStatus.NOT_FOUND);
			}
			AdminEntity admin=adminService.delete(id);
			AdminDto adminDto= new AdminDto(admin.getCode(), admin.getFirstName(), admin.getLastName());
			return new ResponseEntity<AdminDto>(adminDto, HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}
