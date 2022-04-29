package com.sapient.blog.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sapient.blog.entities.Category;
import com.sapient.blog.entities.Post;
import com.sapient.blog.entities.User;
import com.sapient.blog.exceptions.ResourceNotFoundException;
import com.sapient.blog.payloads.PostDto;
import com.sapient.blog.payloads.PostResponse;
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
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else if(sortDir.equalsIgnoreCase("desc")) {
			sort=Sort.by(sortBy).descending();
		}else {
			sort=Sort.by(sortBy).ascending();
		}
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalRecords(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
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
		List<Post> posts = this.postRepo.findByTitle("%"+keyword+"%");
		List<PostDto>postDtos= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

}
