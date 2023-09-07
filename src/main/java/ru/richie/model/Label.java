package ru.richie.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class Label {
    private Long id;
    private String name;
    private Status status;

    public Label(Long id) {
        this.id = id;
    }

}
