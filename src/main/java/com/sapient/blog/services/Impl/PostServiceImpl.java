package com.sapient.blog.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.blog.entities.Category;
import com.sapient.blog.entities.Post;
import com.sapient.blog.entities.User;
import com.sapient.blog.exceptions.ResourceNotFoundException;
import com.sapient.blog.payloads.PostDto;
import com.sapient.blog.repositories.CategoryRepo;
import com.sapient.blog.repositories.PostRepo;
import com.sapient.blog.repositories.UserRepo;
import com.sapient.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto,Long userID,Long categoryID) {
		
		User user=this.userRepo.findById(userID).orElseThrow(()->new ResourceNotFoundException("User ","user id", userID));
		Category category=this.categoryRepo.findById(categoryID).orElseThrow(()->new ResourceNotFoundException("Category ","category id", categoryID));

		
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {
		
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepo.save(post);

		return this.modelMapper.map(updatedPost,PostDto.class) ;
	}

	@Override
	public void deletePost(Long postId) {
		
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post id", postId));
		this.postRepo.delete(post);
//		this.postRepo.deleteById(postId);


	}

	@Override
	public List<PostDto> getAllPosts() {
		
		List<Post> allPosts=this.postRepo.findAll();
		List<PostDto> postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public PostDto getPostById(Long postId) {
		
		Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post id", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Long categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
		
		List<Post>posts= this.postRepo.findByCategory(category);
		
		List<PostDto>postDtos= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Long userId) {
	User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
		
		List<Post>posts= this.postRepo.findByUser(user);
		
		List<PostDto>postDtos= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		return null;
	}

}
