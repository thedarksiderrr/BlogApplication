package com.myapp.blog.service;

import com.myapp.blog.DTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, Long userId);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUser();

    void deleteUser(Long userId);
}
