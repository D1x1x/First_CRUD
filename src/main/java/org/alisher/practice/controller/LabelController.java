package org.alisher.practice.controller;

import org.alisher.practice.model.Label;
import org.alisher.practice.repository.LabelRepository;

import java.util.List;

public class LabelController {

    private final LabelRepository labelRepository;

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label save(Label label) {
        return labelRepository.save(label);
    }

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }

    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public Label update(Label label) {
        return labelRepository.update(label);
    }

    public void deleteById(Long id) {
        labelRepository.deleteById(id);
    }
}