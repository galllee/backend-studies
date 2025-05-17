package org.example.service;

import org.example.domain.Post;
import org.example.repository.PostRepository;
import org.example.util.IdGenerator;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository postRepository = new PostRepository();
    private final IdGenerator idGenerator = new IdGenerator();

    public void createPost(final String title) {
        Integer id = idGenerator.createId();
        postRepository.save(id, title);
    }

    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    public Post getPostById(Integer id) {
        return postRepository.getPostById(id);
    }

    public boolean updatePostTitle(Integer id, String newTitle) {
        return postRepository.updatePostTitle(id, newTitle);
    }

    public boolean deletePostById(Integer id) {
        return postRepository.deletePostById(id);
    }

    public List<Post> getPostsByKeyword(final String keyword) {
        return postRepository.getPostsByKeyword(keyword);
    }

}
