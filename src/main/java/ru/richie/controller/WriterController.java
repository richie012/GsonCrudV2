package ru.richie.controller;

import lombok.RequiredArgsConstructor;
import ru.richie.model.Label;
import ru.richie.model.Post;
import ru.richie.model.Writer;
import ru.richie.repositories.WriterRepo;

import java.util.List;

@RequiredArgsConstructor
public class WriterController {
    private final WriterRepo writerRepo;

    public Writer addWriter(String firstName, String lastName, List<Post> posts){
        Writer writer = Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .posts(posts)
                .build();
        writerRepo.add(writer);
        return writer;
    }
    public Writer getWriterById(Long id){
        return writerRepo.getById(id);
    }

    public List<Writer> getAllWriters(){
        return writerRepo.getAll();
    }
    public Writer updateWriter(Long id, String firstName, String lastName){
        Writer writer = Writer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return writerRepo.update(writer);
    }
    public Writer addPostToWriter(Long id, Post post){
        Writer writer = writerRepo.getById(id);
        List<Post> writerPosts = writer.getPosts();

        writerPosts.add(post);
        writer.setPosts(writerPosts);

        writerRepo.update(writer);
        return writer;
    }
    public boolean deleteWriterById(Long id){
        return writerRepo.deleteById(id);
    }
}
