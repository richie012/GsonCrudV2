package ru.richie.model;

import lombok.*;
import ru.richie.enums.Status;

@Data
@Builder
public class Label {
    private Long id;
    private String name;
    private Status status;

    public Label(Long id) {
        this.id = id;
    }

    public Label(Long id, String name) {
        this.id = id;
        this.name = name;
        this.status = Status.ACTIVE;
    }
}
