package ru.richie.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ru.richie.model.Status;
import ru.richie.model.Label;
import ru.richie.repository.LabelRepo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class LabelRepositoryImp implements LabelRepo {

    private static final String FILE_PATH = "src/main/resources/label.json";
    private final Gson GSON = new Gson();

    public LabelRepositoryImp() {
    }


    @Override
    public List<Label> getAll() {
        return getLabels();
    }

    private List<Label> getLabels() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<Label>>() {
            }.getType();
            List<Label> listOfLabels = GSON.fromJson(reader, type);

            if (listOfLabels == null) {
                return Collections.emptyList();
            }
            return listOfLabels;

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public Label add(Label label) {
        List<Label> currentLabels = getLabels();
        if (label.getId() != null) {
            label.setId(getNextId());
        }
        currentLabels.add(label);
        addLabels(currentLabels);
        return label;
    }

    private void addLabels(List<Label> labels) {
        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {
            GSON.toJson(labels, fileWriter);
        } catch (Exception ignored) {
        }
    }

    @Override
    public Label getById(Long id) {
        return getLabels()
                .stream()
                .filter(l -> l.getId().equals(id))
                .filter(l -> l.getStatus().equals(Status.DELETED))
                .findFirst()
                .orElse(new Label(getNextId()));
    }


    @Override
    public Label update(Label updatedLabel) {
        List<Label> updatedLiftOfLabel = getLabels()
                .stream()
                .map(label -> {
                    if (Objects.equals(label.getId(), updatedLabel.getId())) {
                        return updatedLabel;
                    } else {
                        return label;
                    }
                })
                .collect(Collectors.toList());

        addLabels(updatedLiftOfLabel);

        return updatedLabel;
    }

    @Override
    public boolean deleteById(Long id) {
        List<Label> currentLabels = getLabels();
        List<Label> updatedLabels = currentLabels
                .stream()
                .peek(l -> {
                    if (l.getId().equals(id)) {
                        l.setStatus(Status.DELETED);
                    }
                }).toList();
        addLabels(updatedLabels);
        return true;

    }


    private Long getNextId() {
        List<Label> currentLabels = getAll();
        return currentLabels
                .stream()
                .mapToLong(Label::getId)
                .max()
                .orElse(0) + 1;

    }
}
