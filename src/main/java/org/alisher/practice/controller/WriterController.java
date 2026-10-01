package org.alisher.practice.controller;

import org.alisher.practice.model.Writer;
import org.alisher.practice.repository.WriterRepository;

import java.util.List;

public class WriterController {

    private final WriterRepository writerRepository;

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    public void deleteById(Long id) {
        writerRepository.deleteById(id);
    }
}