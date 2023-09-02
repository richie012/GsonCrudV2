package ru.richie.model;

import lombok.Builder;
import lombok.Data;
import ru.richie.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
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

    public Post(Long id, String content, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.created = LocalDateTime.now().toString();
        this.labels = labels;
        this.status  = PostStatus.ACTIVE;
    }
}
