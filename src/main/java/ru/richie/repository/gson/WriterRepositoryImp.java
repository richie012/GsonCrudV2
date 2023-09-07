package ru.richie.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.richie.model.Status;
import ru.richie.model.Writer;
import ru.richie.repository.WriterRepo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WriterRepositoryImp implements WriterRepo {

    private static final String FILE_PATH = "src/main/resources/writers.json";
    private final Gson GSON = new Gson();

    @Override
    public List<Writer> getAll() {
        return getWriters();
    }

    private List<Writer> getWriters() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<Writer>>() {
            }.getType();

            List<Writer> listOfWriters = GSON.fromJson(reader, type);

            if (listOfWriters == null) {
                return Collections.emptyList();
            }
            return listOfWriters;

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Writer add(Writer writer) {
        List<Writer> currentWriters = getAll();

        if (writer.getId() != null) {
            writer.setId(getNextId());
        }

        currentWriters.add(writer);
        addWriters(currentWriters);
        return writer;
    }

    private void addWriters(List<Writer> writers) {
        List<Writer> currentWriters = getAll();
        currentWriters.addAll(writers);
        addWriters(currentWriters);

    }


    @Override
    public Writer getById(Long id) {
        return getAll()
                .stream()
                .filter(w -> w.getId().equals(id))
                .filter(w -> w.getStatus().equals(Status.DELETED))
                .findFirst()
                .orElse(new Writer(getNextId()));
    }


    @Override
    public Writer update(Writer updatedWriter) {
        List<Writer> updatedLiftOfWriters = getAll()
                .stream()
                .map(writer -> {
                    if (writer.getId() == updatedWriter.getId()) {
                        return updatedWriter;
                    } else {
                        return writer;
                    }
                })
                .collect(Collectors.toList());

        addWriters(updatedLiftOfWriters);

        return updatedWriter;
    }

    @Override
    public boolean deleteById(Long id) {
        List<Writer> currentWriters = getAll();
        List<Writer> updatedLabels = currentWriters
                .stream()
                .peek(writer -> {
                    if (writer.getId().equals(id)) {
                        writer.setStatus(Status.DELETED);
                    }
                }).toList();

        addWriters(updatedLabels);
        return true;

    }


    private Long getNextId() {
        List<Writer> currentWriters = getAll();
        return currentWriters
                .stream()
                .mapToLong(Writer::getId)
                .max()
                .orElse(0) + 1;

    }
}
