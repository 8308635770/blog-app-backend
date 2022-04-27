package com.sapient.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private Long categoryId;
	@NotEmpty(message = "Category title must be not empty")
	private String categoryTitle;
	@NotEmpty(message = "Category Description must be not empty")
	private String categoryDesc;

}
