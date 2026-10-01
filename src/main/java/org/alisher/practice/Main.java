package org.alisher.practice;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import org.alisher.practice.controller.LabelController;
import org.alisher.practice.controller.PostController;
import org.alisher.practice.controller.WriterController;
import org.alisher.practice.model.Label;
import org.alisher.practice.model.Post;
import org.alisher.practice.model.Writer;
import org.alisher.practice.repository.*;
import org.alisher.practice.view.LabelView;
import org.alisher.practice.view.MainView;
import org.alisher.practice.view.PostView;
import org.alisher.practice.view.WriterView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

//        Label label = new Label("Java");
//        Label label1 = new Label("Streams");
//        Label label2 = new Label("Exception");
//
//        Post post = new Post("Collection", "About collection");
//        Post post1 = new Post("Stream API", "About Stream API");
//
//        post.getLabels().add(label);
//        post1.getLabels().add(label1);
//
//        Writer writer = new Writer("Alisher", "Kadridinov");
//        Writer writer1 = new Writer("Ivan", "Ivanov");
//
//        writer.getPosts().add(post);
//        writer1.getPosts().add(post1);
//
//        List<Writer> writers = new ArrayList<>();
//        writers.add(writer);
//
//

//        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
//        labelRepository.save(label);
//        labelRepository.save(label1);
//
//
//        labelRepository.deleteById(2L);
//        System.out.println(labelRepository.getAll());


//        PostRepository postRepository = new GsonPostRepositoryImpl();
//        postRepository.save(post);
//        postRepository.save(post1);

//        Post postUpdate = postRepository.getById(2L);
//        postUpdate.getLabels().add(label1);
//        postRepository.update(postUpdate);
//
//        postRepository.deleteById(2L);
//
//
//        System.out.println(postRepository.getById(2L));

//        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
//        writerRepository.save(writer);
//        writerRepository.save(writer1);
//
//        Writer writerUpdate = writerRepository.getById(2L);
//        writerUpdate.setFirstName("Petr");
//        writerRepository.update(writerUpdate);
//
//        writerRepository.deleteById(2L);
//
//        System.out.println(writerRepository.getById(2L));

        Scanner scanner = new Scanner(System.in);

        LabelRepository labelRepository = new GsonLabelRepositoryImpl();

        PostRepository postRepository = new GsonPostRepositoryImpl();

        WriterRepository writerRepository = new GsonWriterRepositoryImpl();

        LabelController labelController = new LabelController(labelRepository);

        PostController postController = new PostController(postRepository);

        WriterController writerController = new WriterController(writerRepository);

        LabelView labelView = new LabelView(labelController, scanner);

        PostView postView = new PostView(postController, labelController, scanner);

        WriterView writerView = new WriterView(writerController, postController, scanner);

        MainView mainView = new MainView(labelView, postView, writerView, scanner);

        mainView.run();

        scanner.close();
    }
}
