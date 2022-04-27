package com.sapient.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.blog.entities.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
