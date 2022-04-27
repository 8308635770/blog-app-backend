package com.sapient.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapient.blog.payloads.UserDto;


@Service
public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Long userId);
	UserDto getUserById(Long userId);
	List<UserDto> getAllUsers();
	void deleteUser(Long userId);

}
