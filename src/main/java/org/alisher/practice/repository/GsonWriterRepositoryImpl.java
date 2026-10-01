package org.alisher.practice.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.alisher.practice.model.Status;
import org.alisher.practice.model.Writer;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository{

    private final Gson gson = new Gson();
    private final Path filePath = Path.of("src", "main", "resources", "writers.json");
    private final Type typeWriters = new TypeToken<List<Writer>>() {
    }.getType();

    @Override
    public Writer save(Writer entity) {
        List<Writer> writers = getAll();

        entity.setId(generateId(writers));
        writers.add(entity);
        writeWriters(writers);

        return entity;
    }

    @Override
    public Writer getById(Long id) {

        return getAll().stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    @Override
    public List<Writer> getAll() {

        try (Reader reader = Files.newBufferedReader(
                filePath,
                StandardCharsets.UTF_8
        )) {
            return gson.fromJson(reader, typeWriters);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать writers.json", e);
        }
    }

    @Override
    public Writer update(Writer entity) {
        List<Writer> writers = getAll();

        Writer foundWriter = writers.stream()
                .filter(writer -> writer.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (foundWriter == null) {
            return null;
        }

        foundWriter.setFirstName(entity.getFirstName());
        foundWriter.setLastName(entity.getLastName());
        foundWriter.setPosts(entity.getPosts());

        writeWriters(writers);

        return foundWriter;
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> writers = getAll();

        writers.stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .ifPresent(writer -> {
                    writer.setStatus(Status.DELETED);
                    writeWriters(writers);
                });
    }

    private void writeWriters(List<Writer> writers) {
        try (java.io.Writer writer = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8
        )) {
            gson.toJson(writers, writer);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать writers.json", e);
        }
    }

    private Long generateId(Collection<Writer> writers) {
        return generateNextId(writers, Writer::getId);
    }
}
