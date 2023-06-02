package com.myapp.blog.exceptions;

public class ApiExceptions extends RuntimeException{

    public ApiExceptions(String message){
        super(message);
    }

    public ApiExceptions(){
        super();
    }
}
