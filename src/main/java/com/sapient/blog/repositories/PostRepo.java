package com.sapient.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapient.blog.entities.Category;
import com.sapient.blog.entities.Post;
import com.sapient.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Long> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	

}
