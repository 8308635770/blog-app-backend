package com.sapient.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sapient.blog.entities.Category;
import com.sapient.blog.entities.Post;
import com.sapient.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Long> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.title like:key")
	List<Post> findByTitle(@Param("key") String title);
	
//	List<Post> findByTitleContaining(String title);  Not working due to some bugs in hibernate version 5.6
	

}
