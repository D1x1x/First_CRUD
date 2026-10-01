package org.alisher.practice.controller;

import org.alisher.practice.model.Post;
import org.alisher.practice.repository.PostRepository;

import java.util.List;

public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post update(Post post) {
        return postRepository.update(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}