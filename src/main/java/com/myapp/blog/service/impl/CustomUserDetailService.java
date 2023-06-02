package com.myapp.blog.service.impl;

import com.myapp.blog.entity.User;
import com.myapp.blog.exceptions.ResourceNotFoundException;
import com.myapp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email : " + username, 0L));


        return user;
    }
}
