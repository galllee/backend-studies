package org.example.repository;

import org.example.domain.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostRepository {
    private final Map<Integer, Post> posts = new HashMap<Integer, Post>();

    public void save(Integer id, String title) {
        posts.put(id, new Post(id, title));
    }

    public List<Post> getAllPosts() {
        return posts.values().stream().toList();
    }

    public Post getPostById(Integer id) {
        if (posts.containsKey(id)) {
            return posts.get(id);
        } else {
            return null;
        }
    }

    public boolean updatePostTitle(Integer id, String newTitle) {
        if (posts.containsKey(id)) {
            posts.put(id, new Post(id, newTitle));
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePostById(Integer id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
            return true;
        } {
            return false;
        }
    }

    public List<Post> getPostsByKeyword(String keyword) {
        return posts.values().stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
    }
}

