package org.alisher.practice.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.alisher.practice.model.Label;
import org.alisher.practice.model.Post;
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

public class GsonPostRepositoryImpl implements PostRepository{

    private final Gson gson = new Gson();
    private final Path filePath = Path.of("src", "main", "resources", "posts.json");
    private final Type typePosts = new TypeToken<List<Post>>() {
    }.getType();

    @Override
    public Post save(Post entity) {
        List<Post> posts = getAll();

        entity.setId(generateId(posts));
        posts.add(entity);
        writePosts(posts);

        return entity;
    }

    @Override
    public Post getById(Long id) {
        return getAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Post> getAll() {
        try (Reader reader = Files.newBufferedReader(
                filePath,
                StandardCharsets.UTF_8
        )) {
            return gson.fromJson(reader, typePosts);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать posts.json", e);
        }
    }

    @Override
    public Post update(Post entity) {
        List<Post> posts = getAll();

        Post foundPost = posts.stream()
                .filter(post -> post.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);

        if (foundPost == null) {
            return null;
        }

        foundPost.setTitle(entity.getTitle());
        foundPost.setContent(entity.getContent());
        foundPost.setLabels(entity.getLabels());

        writePosts(posts);

        return foundPost;
    }

    @Override
    public void deleteById(Long id) {
        List<Post> posts = getAll();

        posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .ifPresent(post -> {
                    post.setStatus(Status.DELETED);
                    writePosts(posts);
                });
    }

    private void writePosts(List<Post> posts){
        try(Writer writer = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8
        )){
            gson.toJson(posts,writer);
        }catch (IOException e){
            throw new RuntimeException("Не удалось записать posts.json", e);
        }
    }

    private Long generateId(Collection<Post> posts) {
        return generateNextId(posts, Post::getId);
    }
}
