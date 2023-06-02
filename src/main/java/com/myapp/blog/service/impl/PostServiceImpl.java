package com.myapp.blog.service.impl;

import com.myapp.blog.DTO.PostDTO;
import com.myapp.blog.DTO.response.PostResponse;
import com.myapp.blog.entity.Category;
import com.myapp.blog.entity.Post;
import com.myapp.blog.entity.User;
import com.myapp.blog.exceptions.ResourceNotFoundException;
import com.myapp.blog.repository.CategoryRepository;
import com.myapp.blog.repository.PostRepository;
import com.myapp.blog.repository.UserRepository;
import com.myapp.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

//        category.setPost(null);
//        user.setPost(null);

        Post postEntity = dtoToEntity(postDTO);
        postEntity.setImage("default.png");
        postEntity.setDate(new Date());
        postEntity.setUser(user);
        postEntity.setCategory(category);

        Post createdPost = postRepository.save(postEntity);

        return entityToDto(createdPost);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long pId) {

        Post post = postRepository.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", pId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
//        post.setDate(new Date("Updated Date"));
        post.setImage(postDTO.getImage());
        Post updatedPost = postRepository.save(post);

        return entityToDto(updatedPost);
    }

    @Override
    public void deletePost(Long pId) {
        Post postId = postRepository.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", pId));
        postRepository.delete(postId);
    }

    @Override
    public PostDTO getPostById(Long pId) {

        Post findPostById = postRepository.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", pId));

        return entityToDto(findPostById);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        //sorting according to ascending and descending
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else sort = Sort.by(sortBy).descending();


        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(p);
        List<Post> allPost = pagePost.getContent();

        List<PostDTO> collectAllPost = allPost.stream().map(post -> entityToDto(post)).collect(Collectors.toList());

        //postResponse - paging and sorting
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collectAllPost);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public PostResponse getAllPostByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category categoryById = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        //sorting according to ascending and descending
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else sort = Sort.by(sortBy).descending();


        //pageable
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> pagePost = postRepository.findByCategory(categoryById, p);
        List<Post> pagePostContent = pagePost.getContent();

        List<PostDTO> AllPostByCategory = pagePostContent.stream().map((post) -> entityToDto(post)).collect(Collectors.toList());
//                .map((post) -> mapper.map(findAllPostByCategoryId, PostDTO.class))

        //postResponse - paging and sorting
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(AllPostByCategory);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getAllPostByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        User userById = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        //sorting according to ascending and descending
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else sort = Sort.by(sortBy).descending();

        //pageable
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)); // user ".descending()" to print in descending oder

        Page<Post> pagePost = postRepository.findByUser(userById, p);
        List<Post> pagePostContent = pagePost.getContent();

        List<PostDTO> AllPostByUsers = pagePostContent.stream().map((post) -> entityToDto(post))
//                .map((post) -> mapper.map(pagePost, PostDTO.class))
                .collect(Collectors.toList());

        //PostResponse - paging and sorting
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(AllPostByUsers);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> searchPosts(String keywords) {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keywords);

        List<PostDTO> allPostByKeyword = posts.stream().map((post) -> entityToDto(post)).collect(Collectors.toList());

        return allPostByKeyword;
    }

    //-----------------------------------------------------------------------
    private Post dtoToEntity(PostDTO postDTO) {
        Post postEntity = mapper.map(postDTO, Post.class);
        return postEntity;
    }

    private PostDTO entityToDto(Post postEntity) {
        PostDTO postDTO = mapper.map(postEntity, PostDTO.class);
        return postDTO;
    }


}
