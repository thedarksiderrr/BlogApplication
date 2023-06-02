package com.myapp.blog.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Long categoryId;

    @NotEmpty(message = "Title can't be Empty")
    @Size(min = 2, max = 100, message = "Title must contain {min} - {max} characters")
    private String categoryTitle;

    @NotEmpty(message = "Description can't be Empty")
    @Size(min = 2, max = 500, message = "Description must contain {min} - {max} characters")
    private String categoryDescription;
}
