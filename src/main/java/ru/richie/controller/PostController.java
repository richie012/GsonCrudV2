package ru.richie.controller;

import lombok.RequiredArgsConstructor;
import ru.richie.model.Label;
import ru.richie.model.Post;
import ru.richie.repository.PostRepo;

import java.util.List;

public class PostController {
    public PostController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    private final PostRepo postRepo;

    public Post addPost(String content, List<Label> labels){
        Post post = Post.builder()
                .content(content)
                .labels(labels).build();
        postRepo.add(post);
        return post;
    }
    public Post getPostById(Long id){
        return postRepo.getById(id);
    }
    public List<Post> getAllPosts(){
        return postRepo.getAll();
    }
    public Post updatePost(Long id, String content){
        Post updatedPost = Post.builder()
                .id(id)
                .content(content).build();
        return postRepo.update(updatedPost);
    }
    public Post addLabelToPost(Long id, Label label){
        Post post = getPostById(id);
        List<Label> labels = post.getLabels();
        labels.add(label);
        post.setLabels(labels);
        postRepo.update(post);
        return post;
    }
    public boolean deletePostById(Long id){
        return postRepo.deleteById(id);
    }
}
