package com.example.blogproject.controlller;

import com.example.blogproject.domain.Blog;
import com.example.blogproject.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blogs")
    public Optional<Blog> createBlog(@RequestBody Blog blogParam) {
        return blogService.add
    }
}
