package org.alisher.practice.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.alisher.practice.model.Label;
import org.alisher.practice.model.Status;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository{

    private final Gson gson = new Gson();
    private final Path filePath = Path.of("src", "main", "resources", "labels.json");
    private final Type typeLabels = new TypeToken<List<Label>>() {
    }.getType();

    @Override
    public Label save(Label entity) {
        List<Label> labels = getAll();

        entity.setId(generateId(labels));
        labels.add(entity);
        writeLabels(labels);

        return entity;
    }

    @Override
    public Label getById(Long id) {
        return getAll().stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> getAll() {
        try (Reader reader = Files.newBufferedReader(
                filePath,
                StandardCharsets.UTF_8
        )) {
            return gson.fromJson(reader, typeLabels);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать labels.json", e);
        }
    }

    @Override
    public Label update(Label entity) {
        List<Label> labels = getAll();

        Label foundLabel = labels.stream()
                .filter(label -> label.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (foundLabel == null) {
            return null;
        }

        foundLabel.setName(entity.getName());
        writeLabels(labels);

        return foundLabel;
    }

    @Override
    public void deleteById(Long id) {
        List<Label> labels = getAll();

        labels.stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .ifPresent(label -> {
                    label.setStatus(Status.DELETED);
                    writeLabels(labels);
                });
    }

    private void writeLabels(List<Label> labels){
        try(Writer writer = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8
        )){
            gson.toJson(labels,writer);
        }catch (IOException e){
            throw new RuntimeException("Не удалось записать labels.json", e);
        }
    }

    private Long generateId(Collection<Label> labels) {
        return generateNextId(labels, Label::getId);
    }
}
