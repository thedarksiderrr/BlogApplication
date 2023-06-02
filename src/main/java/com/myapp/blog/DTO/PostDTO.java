package com.myapp.blog.DTO;

import com.myapp.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Long postId;
    private String title;
    private String content;
    private Date date;
    private String image;
    private CategoryDTO category;
    private UserDTO user;
    private Set<CommentDTO> comment = new HashSet<>();
}
