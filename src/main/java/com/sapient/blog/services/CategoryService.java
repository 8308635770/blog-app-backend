package com.sapient.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapient.blog.payloads.CategoryDto;


@Service
public interface CategoryService {
	
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	
	public void deleteCategory(Long categoryId);
	
	public CategoryDto getCategory(Long categoryId);
	
	public List<CategoryDto> getAllCategories();
	
	
	
	

}
