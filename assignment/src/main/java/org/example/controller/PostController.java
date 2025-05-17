package org.example.controller;

import org.example.domain.Post;
import org.example.service.PostService;

import java.util.List;
import java.util.Optional;

public class PostController {
    private final PostService postService = new PostService();

    public void createPost(final String title) {
        postService.createPost(title);
    }

    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    public Post getPostById(final int id) {
        return postService.getPostById(id);
    }

    public boolean updatePostTitle(Integer id, String newTitle) {
        return postService.updatePostTitle(id, newTitle);
    }

    public boolean deletePostById(final int id) {
        return postService.deletePostById(id);
    }

    public List<Post> searchPostsByKeyword(final String keyword) {
        return postService.getPostsByKeyword(keyword);
    }
}
