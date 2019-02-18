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
import com.iktpreobuka.ediary.entities.PersonEntity;
import com.iktpreobuka.ediary.entities.URoleEntity;
import com.iktpreobuka.ediary.entities.UserEntity;
import com.iktpreobuka.ediary.entities.dto.PersonDto;
import com.iktpreobuka.ediary.entities.dto.URoleDto;
import com.iktpreobuka.ediary.entities.dto.UserDto;
import com.iktpreobuka.ediary.services.PersonService;
import com.iktpreobuka.ediary.services.URoleService;
import com.iktpreobuka.ediary.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private URoleService uRoleService;
	@Autowired
	private PersonService personService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		try {
			List<UserEntity> users=userService.getAll();
			List<UserDto> usersDto=new ArrayList<>();
				for(UserEntity user: users) {
					URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
					PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
					UserDto userDto= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
					usersDto.add(userDto);
				}
				return new ResponseEntity<List<UserDto>>(usersDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		try {
			UserEntity user= userService.getById(id);
			if(user==null) {
				return new ResponseEntity<RESTError>(new RESTError("User not found."), HttpStatus.NOT_FOUND);
			}
			URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
			PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
			UserDto userDto= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}

	//kreiranje user profila, ako uloga nije prosledjena, u bazu ce se upisati odgovarajuca uloga
	//                        ako jeste prosledjena i uloga, onda ce se ta uloga upisati zajedno sa osobom
	@RequestMapping(method=RequestMethod.POST, value="/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			PersonEntity person= personService.findByCode(userDto.getPersonDto().getCode());
			if(person==null) {
				return new ResponseEntity<RESTError>(new RESTError("Person not found."), HttpStatus.NOT_FOUND);
			}
			if(userDto.getuRoleDto().getName()!= null ) {
				URoleEntity role= uRoleService.findByName(userDto.getuRoleDto().getName());
				if(role==null) {
					return new ResponseEntity<RESTError>(new RESTError("User role not found."), HttpStatus.NOT_FOUND);
				}
				UserEntity user=userService.save(new UserEntity(userDto.getPassword(), userDto.getUsername(), role, person));
				if(user==null) {
					return new ResponseEntity<RESTError>(new RESTError("Person can not have two user profiles with a same role."), HttpStatus.FORBIDDEN);
				}
				URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
				PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
				UserDto userDtoo= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
				return new ResponseEntity<UserDto>(userDtoo, HttpStatus.OK);
			}else {
			UserEntity user=userService.createUser(new UserEntity( userDto.getPassword(), userDto.getUsername()), person);
			if(user==null) {
				return new ResponseEntity<RESTError>(new RESTError("Person can not have two user profiles with a same role."), HttpStatus.FORBIDDEN);
			}
			URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
			PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
			UserDto userDtoo= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
			return new ResponseEntity<UserDto>(userDtoo, HttpStatus.OK);
			}
		}catch(Exception e) {
		return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	}
	
    // promena username i password 
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public ResponseEntity<?> updateUsernamePassword(@PathVariable Long id, @Valid @RequestBody UserDto userDto,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			UserEntity user= userService.getById(id);
			if(user==null) {
				return new ResponseEntity<RESTError>(new RESTError("No user found."), HttpStatus.NOT_FOUND);
			}
			PersonEntity person= personService.findByCode(userDto.getPersonDto().getCode());
			if(person==null) {
				return new ResponseEntity<RESTError>(new RESTError("Person not found."), HttpStatus.NOT_FOUND);
			}
			user=userService.update(id, new UserEntity( userDto.getPassword(), userDto.getUsername()));
			URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
			PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
			UserDto userDtoo= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
			return new ResponseEntity<UserDto>(userDtoo, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		   }
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}/updateRole")
	public ResponseEntity<?> updateRole(@PathVariable Long id, @Valid @RequestBody String roleName,  BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		try {
			UserEntity user= userService.getById(id);
			if(user==null) {
				return new ResponseEntity<RESTError>(new RESTError("No user found."), HttpStatus.NOT_FOUND);
			}
			URoleEntity role= uRoleService.findByName(roleName);
			if(role==null) {
				return new ResponseEntity<RESTError>(new RESTError("Role not found."), HttpStatus.NOT_FOUND);
			}
			user= userService.updateRole(id, role);
			if(user==null) {
				return new ResponseEntity<RESTError>(new RESTError("Person can not have two user profiles with a same role."), HttpStatus.FORBIDDEN);
			}
			URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
			PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
			UserDto userDtoo= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
			return new ResponseEntity<UserDto>(userDtoo, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		   }
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			if(userService.getById(id)==null) {
				return new ResponseEntity<RESTError>(new RESTError("No user found."), HttpStatus.NOT_FOUND);
			}
			UserEntity user= userService.delete(id);
			URoleDto uRoleDto = new URoleDto(user.getUserRole().getCode(), user.getUserRole().getName());
			PersonDto personDto= new PersonDto(user.getPerson().getCode(), user.getPerson().getFirstName(), user.getPerson().getLastName());
			UserDto userDto= new UserDto(user.getUsername(), user.getPassword(), personDto, uRoleDto);
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	private String createErrorMessage(BindingResult result) {
		return
		result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
}
