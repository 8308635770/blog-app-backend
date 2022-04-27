package com.sapient.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.blog.payloads.ApiResponse;
import com.sapient.blog.payloads.UserDto;
import com.sapient.blog.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userID}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Long userID){
		UserDto updatedUser= this.userService.updateUser(userDto, userID);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	@DeleteMapping("/{userID}")
	public ResponseEntity<?> deleteUser(@PathVariable("userID") Long userId){
		this.userService.deleteUser(userId);
//		return new ResponseEntity(Map.of("Message","User deleted succesfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	
	@GetMapping("/{userID}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long userID){
		return ResponseEntity.ok(this.userService.getUserById(userID));
	}	
	
	

}
