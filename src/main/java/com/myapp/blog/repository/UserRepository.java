package com.myapp.blog.repository;

import com.myapp.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //loading user from database by username
    Optional<User> findByEmail(String email);
}
