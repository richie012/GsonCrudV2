package ru.richie.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@Builder
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status;

    public Writer(Long id) {
        this.id = id;
    }

}
