package com.myapp.blog.service;


import com.myapp.blog.DTO.PostDTO;
import com.myapp.blog.DTO.response.PostResponse;

import java.util.List;

public interface PostService {

    //create post
    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);

    //update post
    PostDTO updatePost(PostDTO postDTO, Long pId);

    //delete post
    void deletePost(Long pId);

    //get single post
    PostDTO getPostById(Long pId);

    //getAll post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get all posts by category
    PostResponse getAllPostByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get all posts by user
    PostResponse getAllPostByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //search post by keyword
    List<PostDTO> searchPosts(String keywords);


}
