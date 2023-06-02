package com.myapp.blog.service.impl;

import com.myapp.blog.DTO.CommentDTO;
import com.myapp.blog.entity.Comment;
import com.myapp.blog.entity.Post;
import com.myapp.blog.exceptions.ResourceNotFoundException;
import com.myapp.blog.repository.CommentRepository;
import com.myapp.blog.repository.PostRepository;
import com.myapp.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper mapper;

    //create comment
    @Override
    public CommentDTO commentCreate(CommentDTO commentDTO, Long postId) {
        Post postById = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment commentEntity = dtoToEntity(commentDTO);
        commentEntity.setPost(postById);
        commentEntity.setCreatedDate(new Date());
        Comment savedComment = commentRepository.save(commentEntity);

        return entityToDto(savedComment);
    }

    //delete comment
    @Override
    public void commentDelete(Long commentId) {

        Comment commentById = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(commentById);
    }

    //----------------------------------------------------------------------------
    private Comment dtoToEntity(CommentDTO commentDTO) {
        Comment commentEntity = mapper.map(commentDTO, Comment.class);
        return commentEntity;
    }

    private CommentDTO entityToDto(Comment commentEntity) {
        CommentDTO commentDTO = mapper.map(commentEntity, CommentDTO.class);
        return commentDTO;
    }
}