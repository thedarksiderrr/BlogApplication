package com.myapp.blog;

import com.myapp.blog.config.AppConstants;
import com.myapp.blog.entity.Role;
import com.myapp.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(passwordEncoder.encode("123456"));

        try {
            Role roleAdmin = new Role();
            roleAdmin.setId(AppConstants.ROLE_ADMIN);
            roleAdmin.setRole("ROLE_ADMIN");

            Role roleUser = new Role();
            roleUser.setId(AppConstants.ROLE_USER);
            roleUser.setRole("ROLE_USER");

            List<Role> roleList = Arrays.asList(roleAdmin, roleUser);

            List<Role> savedRoles = roleRepository.saveAll(roleList);

            savedRoles.forEach(role -> {
                System.out.println(role.getRole());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
