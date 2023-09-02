package ru.richie.controller;

import lombok.RequiredArgsConstructor;
import ru.richie.model.Label;
import ru.richie.repositories.LabelRepo;

import java.util.List;

@RequiredArgsConstructor
public class LabelController {
    private final LabelRepo labelRepo;

    public Label addLabel(String name) {
        Label label = Label.builder().name(name).build();
        labelRepo.add(label);
        return label;
    }

    public Label getLabelById(Long id) {
        return labelRepo.getById(id);
    }

    public List<Label> getAllLabel() {
        return labelRepo.getAll();
    }

    public Label updateLabel(Long id, String name) {
        Label updatedLabel = Label.builder()
                .id(id)
                .name(name)
                .build();
        return labelRepo.update(updatedLabel);
    }
    public boolean deleteLabelById(Long id){
        return labelRepo.deleteById(id);
    }
}
