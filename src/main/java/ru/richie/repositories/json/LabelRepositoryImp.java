package ru.richie.repositories.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ru.richie.enums.Status;
import ru.richie.model.Label;
import ru.richie.repositories.LabelRepo;

import java.io.*;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class LabelRepositoryImp implements LabelRepo {

    private static final String FILE_PATH = "src/main/resources/label.json";
    private final Gson GSON = new Gson();

    public LabelRepositoryImp() {
    }


    @Override
    public List<Label> getAll() {
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

    public boolean addLabels(List<Label> labels) {
        List<Label> currentLabels = getAll();

        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {



            currentLabels.addAll(labels);

            GSON.toJson(currentLabels, fileWriter);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean add(Label label) {
        List<Label> currentLabels = getAll();

        try (Writer fileWriter = new FileWriter(FILE_PATH)) {

            if (label.getId() != null) {
                label.setId(getNextId());
            }
            currentLabels.add(label);
            GSON.toJson(currentLabels, fileWriter);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Label getById(Long id) {
        return getAll()
                .stream()
                .filter(l -> l.getId().equals(id))
                .filter(l -> l.getStatus().equals(Status.DELETED))
                .findFirst()
                .orElse(new Label(getNextId()));
    }


    @Override
    public Label update(Label updatedLabel) {
        List<Label> updatedLiftOfLabel = getAll()
                .stream()
                .map(label -> {
                    if (label.getId() == updatedLabel.getId()) {
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
        List<Label> currentLabels = getAll();
        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {

            List<Label> updatedLabels = currentLabels
                    .stream()
                    .peek(l -> {
                        if (l.getId().equals(id)) {
                            l.setStatus(Status.DELETED);
                        }
                    }).toList();

            GSON.toJson(updatedLabels, fileWriter);
            return true;

        } catch (IOException e) {
            return false;
        }
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
