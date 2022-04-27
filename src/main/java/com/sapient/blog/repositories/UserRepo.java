package com.sapient.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.blog.entities.User;


@Repository
public interface UserRepo extends JpaRepository<User,Long> {

}
