package ru.richie.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class Post {
    private Long id;
    private String content;
    private String created;
    private String updated;
    private List<Label> labels;
    private PostStatus status;

    public Post(Long id) {
        this.id = id;
    }

}
