package com.codepresso.codepressoblog.controller;

import com.codepresso.codepressoblog.controller.dto.PostRequestDto;
import com.codepresso.codepressoblog.controller.dto.PostResponseDto;
import com.codepresso.codepressoblog.service.PostService;
import com.codepresso.codepressoblog.vo.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public List<PostResponseDto> getPostList(@RequestParam Integer page) {
        List<Post> postList = postService.getPostByPage(page, 3);

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }

        return postResponseDtoList;
    }

    @PostMapping("/post")
    public String createPost(@RequestBody PostRequestDto postDto) {
        Post post = postDto.getPost();
        postService.savePost(post);

        return "success";
    }
}
