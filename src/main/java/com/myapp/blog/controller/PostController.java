package com.myapp.blog.controller;

import com.myapp.blog.DTO.PostDTO;
import com.myapp.blog.DTO.response.ApiResponse;
import com.myapp.blog.DTO.response.PostResponse;
import com.myapp.blog.config.AppConstants;
import com.myapp.blog.service.FileService;
import com.myapp.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId) {

        PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    // update post
    @PutMapping("/posts/{userId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("userId") Long userId) {
        PostDTO updatePost = postService.updatePost(postDTO, userId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    // get all post by user (by Post Response)
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse allPostByUser = postService.getAllPostByUser(userId, pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(allPostByUser, HttpStatus.OK);
    }

    // get all post by category (by Post Response)
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse allPostByCategory = postService.getAllPostByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(allPostByCategory, HttpStatus.OK);
    }

    //get all post (by Post Response)
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse allPost = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);

    }

    //get post by id
    @GetMapping("/posts/{pId}")
    public ResponseEntity<?> getPostById(@PathVariable("pId") Long pId) {

        PostDTO getPostById = postService.getPostById(pId);

        return ResponseEntity.ok(getPostById);
    }

    //delete post
    @DeleteMapping("/posts/{pId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("pId") Long pId) {

        postService.deletePost(pId);

        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    //search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keyword") String keyword) {
        List<PostDTO> result = postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Long postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDTO.setImage(fileName);
        PostDTO updatePost = postService.updatePost(postDTO, postId);

        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
    }

    //get image resource
    //http://localhost:9090/api/posts/image/06cd723c-8049-4beb-aae5-14f1478322e8.jpg
    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
