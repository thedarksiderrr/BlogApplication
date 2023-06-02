package com.myapp.blog.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //this method is executed when unauthorized person tries to access authorized API.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //we will send an unauthorized status or error with the help of "HttpServletResponse".
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");


    }
}
