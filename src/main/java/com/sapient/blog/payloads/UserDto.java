package com.sapient.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private Long id;
	@NotEmpty
	@Size(min = 4,message = "Username must be minimum of 4 characters")
	private String name;
	
	@Email(message = "Your email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 4,max = 10,message="password must be minimum of 4 chars and maximum of 10 chars")
//	@Pattern()
	private String password;
	
	@NotEmpty
	private String about;

}
