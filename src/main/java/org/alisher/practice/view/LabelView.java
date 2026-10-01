package org.alisher.practice.view;

import org.alisher.practice.controller.LabelController;
import org.alisher.practice.model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView {

    private final LabelController labelController;
    private final Scanner scanner;

    public LabelView(LabelController labelController, Scanner scanner) {
        this.labelController = labelController;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println();
            System.out.println("Работа с метками:");
            System.out.println("1. Создать метку");
            System.out.println("2. Найти метку по id");
            System.out.println("3. Показать все метки");
            System.out.println("4. Изменить метку");
            System.out.println("5. Удалить метку");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1" -> create();
                    case "2" -> getById();
                    case "3" -> getAll();
                    case "4" -> update();
                    case "5" -> delete();
                    case "0" -> {
                        return;
                    }
                    default ->
                            System.out.println("Неизвестная команда");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: id должен быть числом");
            }
        }
    }

    private void create() {
        System.out.print("Введите название метки: ");
        String name = scanner.nextLine();

        Label label = new Label(name);
        Label savedLabel = labelController.save(label);

        System.out.println("Создана метка: " + savedLabel);
    }

    private void getById() {
        getAll();
        System.out.print("Введите id метки: ");
        Long id = Long.parseLong(scanner.nextLine());
        Label label = labelController.getById(id);

        if (label == null) {
            System.out.println("Метка не найдена");
        } else {
            System.out.println(label);
        }
    }

    private void getAll() {
        List<Label> labels = labelController.getAll();

        if (labels.isEmpty()) {
            System.out.println("Список меток пуст");
            return;
        }

        System.out.println("Список меток:");
        labels.forEach(System.out::println);
    }

    private void update() {
        getAll();
        System.out.print("Введите id метки: ");
        Long id = Long.parseLong(scanner.nextLine());
        Label label = labelController.getById(id);

        if (label == null) {
            System.out.println("Метка не найдена");
            return;
        }
        System.out.print("Введите новое название: ");
        String newName = scanner.nextLine();
        label.setName(newName);
        Label updatedLabel = labelController.update(label);
        System.out.println("Метка изменена: " + updatedLabel);
    }

    private void delete() {
        getAll();
        System.out.print("Введите id метки: ");
        Long id = Long.parseLong(scanner.nextLine());
        Label label = labelController.getById(id);

        if (label == null) {
            System.out.println("Метка не найдена");
            return;
        }

        labelController.deleteById(id);
        System.out.println("Метка удалена");
    }
}