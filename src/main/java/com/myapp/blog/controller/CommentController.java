package com.myapp.blog.controller;

import com.myapp.blog.DTO.CommentDTO;
import com.myapp.blog.DTO.response.ApiResponse;
import com.myapp.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO,
            @PathVariable("postId") Long postId) {

        CommentDTO commentCreate = commentService.commentCreate(commentDTO, postId);

        return new ResponseEntity<CommentDTO>(commentCreate, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.commentDelete(commentId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
    }
}
