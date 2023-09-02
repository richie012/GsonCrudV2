package ru.richie.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import ru.richie.enums.Status;

import java.util.List;
@Data
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

    public Writer(Long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.status = Status.ACTIVE;
    }
}
