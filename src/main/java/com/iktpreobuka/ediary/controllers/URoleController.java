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
import com.iktpreobuka.ediary.entities.URoleEntity;
import com.iktpreobuka.ediary.entities.dto.URoleDto;
import com.iktpreobuka.ediary.services.URoleService;

@RestController
@RequestMapping("/roles")
public class URoleController {

	@Autowired
	private URoleService uRoleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllRoles(){
		try {
			List<URoleEntity> roles= uRoleService.getAll();
			List<URoleDto> rolesDto= new ArrayList<>();
			for(URoleEntity userRole: roles ) {
					URoleDto roleDto= new URoleDto(userRole.getCode(), userRole.getName());
					rolesDto.add(roleDto);
			}
			return new ResponseEntity<List<URoleDto>>(rolesDto,  HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
		try {
			URoleEntity uRole=uRoleService.getById(id);
			if(uRole!=null) {
				URoleDto roleDto=new URoleDto(uRole.getCode(), uRole.getName());
				return new ResponseEntity<URoleDto>(roleDto, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError("No role found."), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createRole(@Valid @RequestBody URoleDto roleDto, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			URoleEntity uRole=uRoleService.save(new URoleEntity(roleDto.getCode(), roleDto.getName()));
			if(uRole!=null) {
				URoleDto URoleDto=new URoleDto(uRole.getCode(), uRole.getName());
				return new ResponseEntity<URoleDto>(URoleDto, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError("No role found."), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{id}")
    public ResponseEntity<?> createRole(@PathVariable Long id, @Valid @RequestBody URoleDto roleDto, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			URoleEntity uRol=uRoleService.getById(id);
			if(uRol==null) {
				return new ResponseEntity<RESTError>(new RESTError("No role found."), HttpStatus.NOT_FOUND);
			}
			URoleEntity uRole=uRoleService.put(id, new URoleEntity(roleDto.getCode(), roleDto.getName()));
			URoleDto URoleDto=new URoleDto(uRole.getCode(), uRole.getName());
			return new ResponseEntity<URoleDto>(URoleDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
		try {
			URoleEntity uRol=uRoleService.getById(id);
			if(uRol==null) {
				return new ResponseEntity<RESTError>(new RESTError("No role found."), HttpStatus.NOT_FOUND);
			}
			URoleEntity uRole=uRoleService.deleteById(id);
			if(uRole==null) {
				return new ResponseEntity<RESTError>(new RESTError("Use role is not deleted, because one or more users releted to it."), HttpStatus.FORBIDDEN);
			}
			URoleDto roleDto=new URoleDto(uRole.getCode(), uRole.getName());
			return new ResponseEntity<URoleDto>(roleDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
}
