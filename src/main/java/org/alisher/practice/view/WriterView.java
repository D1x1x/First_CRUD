package org.alisher.practice.view;

import org.alisher.practice.controller.PostController;
import org.alisher.practice.controller.WriterController;
import org.alisher.practice.model.Post;
import org.alisher.practice.model.Writer;

import java.util.List;
import java.util.Scanner;

public class WriterView {

    private final WriterController writerController;
    private final PostController postController;
    private final Scanner scanner;

    public WriterView(WriterController writerController, PostController postController, Scanner scanner) {
        this.writerController = writerController;
        this.postController = postController;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println();
            System.out.println("Работа с авторами:");
            System.out.println("1. Создать автора");
            System.out.println("2. Найти автора по id");
            System.out.println("3. Показать всех авторов");
            System.out.println("4. Изменить автора");
            System.out.println("5. Удалить автора");
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
        System.out.print("Введите имя автора: ");
        String firstName = scanner.nextLine();

        System.out.print("Введите фамилию автора: ");
        String lastName = scanner.nextLine();

        Writer writer = new Writer(firstName, lastName);

        System.out.print("Добавить пост? 1 - да, 0 - нет: ");
        String answer = scanner.nextLine();

        while (answer.equals("1")) {
            showPosts();

            System.out.print("Введите id поста: ");
            Long postId = Long.parseLong(scanner.nextLine());

            Post post = postController.getById(postId);

            if (post == null) {
                System.out.println("Пост не найден");
            } else {
                writer.getPosts().add(post);
                System.out.println("Пост добавлен");
            }
            System.out.print("Добавить ещё пост? 1 - да, 0 - нет: ");
            answer = scanner.nextLine();
        }

        Writer savedWriter = writerController.save(writer);
        System.out.println("Создан автор: " + savedWriter);
    }

    private void getById() {
        getAll();

        System.out.print("Введите id автора: ");
        Long id = Long.parseLong(scanner.nextLine());

        Writer writer = writerController.getById(id);

        if (writer == null) {
            System.out.println("Автор не найден");
        } else {
            System.out.println(writer);
        }
    }

    private void getAll() {
        List<Writer> writers = writerController.getAll();

        if (writers.isEmpty()) {
            System.out.println("Список авторов пуст");
            return;
        }

        System.out.println("Список авторов:");

        writers.forEach(System.out::println);
    }

    private void update() {
        getAll();
        System.out.print("Введите id автора: ");
        Long id = Long.parseLong(scanner.nextLine());

        Writer writer = writerController.getById(id);

        if (writer == null) {
            System.out.println("Автор не найден");
            return;
        }

        System.out.print("Введите новое имя: ");
        String newFirstName = scanner.nextLine();
        System.out.print("Введите новую фамилию: ");
        String newLastName = scanner.nextLine();

        writer.setFirstName(newFirstName);
        writer.setLastName(newLastName);

        Writer updatedWriter = writerController.update(writer);

        System.out.println("Автор изменён: " + updatedWriter);
    }

    private void delete() {
        getAll();
        System.out.print("Введите id автора: ");
        Long id = Long.parseLong(scanner.nextLine());

        Writer writer = writerController.getById(id);
        if (writer == null) {
            System.out.println("Автор не найден");
            return;
        }

        writerController.deleteById(id);
        System.out.println("Автор удалён");
    }

    private void showPosts() {
        List<Post> posts = postController.getAll();

        if (posts.isEmpty()) {
            System.out.println("Список постов пуст");
            return;
        }

        System.out.println("Доступные посты:");
        posts.forEach(System.out::println);
    }
}