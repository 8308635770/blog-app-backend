package com.sapient.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sapient.blog.payloads.ApiResponse;
import com.sapient.blog.payloads.PostDto;
import com.sapient.blog.payloads.PostResponse;
import com.sapient.blog.services.FileService;
import com.sapient.blog.services.PostService;
import com.sapient.blog.utils.Appconstants;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId,
			@PathVariable Long categoryId) {

		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);

	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
		
		List<PostDto> posts=this.postService.getAllPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId){
		
		List<PostDto> posts=this.postService.getAllPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts/{postID}")
	public ResponseEntity<PostDto> getPostByPostID(@PathVariable Long postID){

		PostDto postDto=this.postService.getPostById(postID);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false )Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = Appconstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = Appconstants.SORT_DIR,required = false)String sortDir

			){
		PostResponse postResponse=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/posts/{postID}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postID) {
		this.postService.deletePost(postID);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postID}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Long postID){
		PostDto updatedPostDto=this.postService.updatePost(postDto, postID);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable ("keywords") String keywords){
		
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
		
	}
	
	
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image")MultipartFile file,
			@PathVariable("postId")Long postID
			) throws IOException{
		
			PostDto post = this.postService.getPostById(postID);
			String uploadImageName = this.fileService.uploadImage(path, file);
			post.setImageName(uploadImageName);
			PostDto updatePost = this.postService.updatePost(post, postID);
			
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);

	
	}
	
	@GetMapping(value = "/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadFile(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
		
	}

	
	
	
	
	
	
	

}
