package com.blog.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private boolean lastPage;
    private long totalElement;
}
