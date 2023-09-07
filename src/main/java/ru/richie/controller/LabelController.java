package ru.richie.controller;

import lombok.RequiredArgsConstructor;
import ru.richie.model.Label;
import ru.richie.model.Status;
import ru.richie.repository.LabelRepo;

import java.util.List;


public class LabelController {
    private final LabelRepo labelRepo;

    public LabelController(LabelRepo labelRepo) {
        this.labelRepo = labelRepo;
    }

    public Label addLabel(String name) {
        Label label = Label.builder().name(name)
                .status(Status.ACTIVE)
                .build();
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
