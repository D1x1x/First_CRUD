package org.alisher.practice.view;

import org.alisher.practice.controller.LabelController;
import org.alisher.practice.controller.PostController;
import org.alisher.practice.model.Label;
import org.alisher.practice.model.Post;

import java.util.List;
import java.util.Scanner;

public class PostView {

    private final PostController postController;
    private final LabelController labelController;
    private final Scanner scanner;

    public PostView(PostController postController, LabelController labelController, Scanner scanner) {
        this.postController = postController;
        this.labelController = labelController;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println();
            System.out.println("Работа с постами:");
            System.out.println("1. Создать пост");
            System.out.println("2. Найти пост по id");
            System.out.println("3. Показать все посты");
            System.out.println("4. Изменить пост");
            System.out.println("5. Удалить пост");
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
        System.out.print("Введите название поста: ");
        String title = scanner.nextLine();

        System.out.print("Введите содержание поста: ");
        String content = scanner.nextLine();

        Post post = new Post(title, content);

        System.out.print("Добавить метку? 1 - да, 0 - нет: ");
        String answer = scanner.nextLine();

        while (answer.equals("1")) {
            showLabels();

            System.out.print("Введите id метки: ");
            Long labelId = Long.parseLong(scanner.nextLine());

            Label label = labelController.getById(labelId);

            if (label == null) {
                System.out.println("Метка не найдена");
            } else {
                post.getLabels().add(label);
                System.out.println("Метка добавлена");
            }

            System.out.print(
                    "Добавить ещё метку? 1 - да, 0 - нет: "
            );
            answer = scanner.nextLine();
        }

        Post savedPost = postController.save(post);
        System.out.println("Создан пост: " + savedPost);
    }

    private void getById() {
        getAll();
        System.out.print("Введите id поста: ");
        Long id = Long.parseLong(scanner.nextLine());

        Post post = postController.getById(id);

        if (post == null) {
            System.out.println("Пост не найден");
        } else {
            System.out.println(post);
        }
    }

    private void getAll() {
        List<Post> posts = postController.getAll();

        if (posts.isEmpty()) {
            System.out.println("Список постов пуст");
            return;
        }

        System.out.println("Список постов:");
        posts.forEach(System.out::println);
    }

    private void update() {
        getAll();
        System.out.print("Введите id поста: ");
        Long id = Long.parseLong(scanner.nextLine());
        Post post = postController.getById(id);

        if (post == null) {
            System.out.println("Пост не найден");
            return;
        }

        System.out.print("Введите новое название: ");
        String newTitle = scanner.nextLine();

        System.out.print("Введите новое содержание: ");
        String newContent = scanner.nextLine();

        post.setTitle(newTitle);
        post.setContent(newContent);

        Post updatedPost = postController.update(post);
        System.out.println("Пост изменён: " + updatedPost);
    }

    private void delete() {
        getAll();
        System.out.print("Введите id поста: ");
        Long id = Long.parseLong(scanner.nextLine());
        Post post = postController.getById(id);

        if (post == null) {
            System.out.println("Пост не найден");
            return;
        }

        postController.deleteById(id);
        System.out.println("Пост удалён");
    }

    private void showLabels() {
        List<Label> labels = labelController.getAll();

        if (labels.isEmpty()) {
            System.out.println("Список меток пуст");
            return;
        }

        System.out.println("Доступные метки:");
        labels.forEach(System.out::println);
    }
}