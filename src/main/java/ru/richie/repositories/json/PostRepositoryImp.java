package ru.richie.repositories.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.richie.enums.PostStatus;
import ru.richie.enums.Status;
import ru.richie.model.Label;
import ru.richie.model.Post;
import ru.richie.repositories.PostRepo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepositoryImp implements PostRepo {

    private static final String FILE_PATH = "src/main/resources/post.json";
    private final Gson GSON = new Gson();

    public boolean addPosts(List<Post> posts) {
        List<Post> currentPosts = getAll();
        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {

            currentPosts.addAll(posts);
            GSON.toJson(currentPosts, fileWriter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean add(Post post) {
        List<Post> currentPosts = getAll();
        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {
            if (post.getId() != null) {
                post.setId(getNextId());
            }

            currentPosts.add(post);
            GSON.toJson(currentPosts, fileWriter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Post getById(Long id) {
        return getAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .filter(p -> p.getStatus().equals(PostStatus.DELETED))
                .findFirst()
                .orElse(new Post(getNextId()));
    }

    @Override
    public List<Post> getAll() {
        try (Reader fileRider = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<Post>>() {
            }.getType();
            List<Post> listOfPosts = GSON.fromJson(fileRider, type);
            if (listOfPosts == null) {
                return Collections.emptyList();
            }
            return listOfPosts;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Post update(Post updatedPost) {
        List<Post> updatedLiftOfLabel = getAll()
                .stream()
                .map(post -> {
                    if (post.getId() == updatedPost.getId()) {
                        updatedPost.setUpdated(LocalDateTime.now().toString());
                        return updatedPost;
                    } else {
                        return post;
                    }
                })
                .collect(Collectors.toList());

        addPosts(updatedLiftOfLabel);

        return updatedPost;
    }

    @Override
    public boolean deleteById(Long id) {
        List<Post> currentPosts = getAll();
        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {

            List<Post> updatedPosts = currentPosts
                    .stream()
                    .peek(l -> {
                        if (l.getId().equals(id)) {
                            l.setStatus(PostStatus.DELETED);
                        }
                    }).toList();

            GSON.toJson(updatedPosts, fileWriter);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private Long getNextId() {
        List<Post> currentLabels = getAll();
        return currentLabels
                .stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0) + 1;

    }
}
