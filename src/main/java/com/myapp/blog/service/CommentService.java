package com.myapp.blog.service;

import com.myapp.blog.DTO.CommentDTO;

public interface CommentService {

    //create comment
    CommentDTO commentCreate(CommentDTO commentDTO, Long postId);

    //delete comment
    void commentDelete(Long commentId);

}
