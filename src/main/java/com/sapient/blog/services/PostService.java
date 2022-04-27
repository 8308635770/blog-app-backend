package com.sapient.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapient.blog.entities.Post;
import com.sapient.blog.payloads.PostDto;

@Service
public interface PostService {
	
	PostDto createPost(PostDto postDto,Long userId,Long categoryId);
	PostDto updatePost(PostDto postDto,Long postId);
	void deletePost(Long postId);
	List<PostDto> getAllPosts();
	PostDto getPostById(Long postId);
	List<PostDto> getAllPostsByCategory(Long categoryId);
	List<PostDto> getAllPostsByUser(Long userId);
	List<PostDto> searchPosts(String keyword);




}
