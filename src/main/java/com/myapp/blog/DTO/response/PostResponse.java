package com.myapp.blog.DTO.response;

import com.myapp.blog.DTO.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {

    private List<PostDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements; //how many records
    private int totalPages;
    private boolean lastPage;

}
