package org.alisher.practice.view;

import java.util.Scanner;

public class MainView {

    private final LabelView labelView;
    private final PostView postView;
    private final WriterView writerView;
    private final Scanner scanner;

    public MainView(LabelView labelView, PostView postView, WriterView writerView, Scanner scanner) {
        this.labelView = labelView;
        this.postView = postView;
        this.writerView = writerView;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println();
            System.out.println("Главное меню:");
            System.out.println("1. Метки");
            System.out.println("2. Посты");
            System.out.println("3. Авторы");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1" -> labelView.run();
                    case "2" -> postView.run();
                    case "3" -> writerView.run();
                    case "0" -> {
                        System.out.println("Программа завершена");
                        return;
                    }
                    default -> System.out.println("Неизвестная команда");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: id должен быть числом");
            }
        }
    }
}