package ru.richie.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.richie.model.PostStatus;
import ru.richie.model.Post;
import ru.richie.repository.PostRepo;

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

    @Override
    public List<Post> getAll() {
        return getPosts();
    }

    private List<Post> getPosts() {
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
    public Post add(Post post) {
        List<Post> currentPosts = getAll();

        if (post.getId() != null) {
            post.setId(getNextId());
        }

        currentPosts.add(post);
        addPosts(currentPosts);
        return post;

    }

    private void addPosts(List<Post> posts) {
        List<Post> currentPosts = getAll();
        try (java.io.Writer fileWriter = new FileWriter(FILE_PATH)) {
            GSON.toJson(currentPosts, fileWriter);
        } catch (Exception e) {
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
        List<Post> updatedPosts = currentPosts
                .stream()
                .peek(l -> {
                    if (l.getId().equals(id)) {
                        l.setStatus(PostStatus.DELETED);
                    }
                }).toList();
        addPosts(updatedPosts);
        return true;
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
