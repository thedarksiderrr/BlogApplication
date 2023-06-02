package com.myapp.blog.service.impl;

import com.myapp.blog.DTO.UserDTO;
import com.myapp.blog.config.AppConstants;
import com.myapp.blog.entity.Role;
import com.myapp.blog.entity.User;
import com.myapp.blog.exceptions.ResourceNotFoundException;
import com.myapp.blog.repository.RoleRepository;
import com.myapp.blog.repository.UserRepository;
import com.myapp.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        User user = dtoToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findById(AppConstants.ROLE_USER).get();
        user.getRoles().add(role);
        User newUser = userRepository.save(user);

        return entityToDto(newUser);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        //did dto to entity to save inputs in database
        User user = dtoToEntity(userDTO);

        //save user's input property to database
        User saveUser = userRepository.save(user);

        //then did entity to dto for show dto property (user inputted)
        return entityToDto(saveUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

//        if (userId == userDTO.getId()) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(user.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = userRepository.save(user);
        UserDTO userDTO1 = entityToDto(updatedUser);
//        }
        return userDTO1;
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return entityToDto(user);
    }

    @Override
    public List<UserDTO> getAllUser() {

        List<User> allUsers = userRepository.findAll();

        List<UserDTO> collectAllUsers = allUsers.stream()
                .map(user -> this.entityToDto(user))
                .collect(Collectors.toList());

        return collectAllUsers;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);

    }

    //----------------------------------------------------------------------------
    private User dtoToEntity(UserDTO userDTO) {
        
        User userEntity = mapper.map(userDTO, User.class);
        
//        User userEntity = new User();
//        userEntity.setId(userDTO.getId());
//        userEntity.setFirstName(userDTO.getFirstName());
//        userEntity.setLastName(userDTO.getLastName());
//        userEntity.setEmail(userDTO.getEmail());
//        userEntity.setPassword(userDTO.getPassword());
//        userEntity.setAbout(userDTO.getAbout());

        return userEntity;
    }

    private UserDTO entityToDto(User userEntity) {

        UserDTO userDTO = mapper.map(userEntity, UserDTO.class);

//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setFirstName(userEntity.getFirstName());
//        userDTO.setLastName(userEntity.getLastName());
//        userDTO.setEmail(userEntity.getEmail());
//        userDTO.setPassword(userEntity.getPassword());
//        userDTO.setAbout(userEntity.getAbout());

        return userDTO;
    }
}
