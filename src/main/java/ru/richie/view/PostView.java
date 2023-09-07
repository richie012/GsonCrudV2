package ru.richie.view;

import lombok.RequiredArgsConstructor;
import ru.richie.controller.PostController;
import ru.richie.model.Label;
import ru.richie.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PostView {
    private final PostController postController;
    private final Scanner scanner;

    public PostView(PostController postController, Scanner scanner) {
        this.postController = postController;
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("1. Add Post");
            System.out.println("2. Get post by Id");
            System.out.println("3. Get all posts");
            System.out.println("4. Update post");
            System.out.println("5. Delete post");
            System.out.println("6. Add label to post");
            System.out.println("0. Cancel");
            System.out.println("Select an option");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addPost();
                    break;
                case 2:
                    getPostById();
                    break;
                case 3:
                    getAllPosts();
                    break;
                case 4:
                    updatePost();
                    break;
                case 5:
                    deletePost();
                    break;
                case 6:
                    addLabelToPost();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private void addPost() {
        System.out.println("Enter content: ");
        String content = scanner.nextLine();
        List<Label> labels= new ArrayList<>();

        postController.addPost(content, labels);
        System.out.println("Post created: " + content);
    }

    private void getPostById() {
        System.out.println("Enter post's id: ");
        Long id = scanner.nextLong();
        Post post = postController.getPostById(id);
        System.out.println("Post: " + post.toString());
    }

    private void getAllPosts() {
        List<Post> posts = postController.getAllPosts();
        for (Post post:
             posts) {
            System.out.println(post.toString());
        }
    }

    private void updatePost() {
        System.out.println("Enter post's id: ");
        Long id = scanner.nextLong();
        Post oldPost = postController.getPostById(id);
        System.out.println("Old post's data: " + oldPost.toString());
        System.out.println("Enter new Content: ");
        String newContent = scanner.nextLine();
        postController.updatePost(id, newContent);
        System.out.println("Post updated.");
    }

    private void deletePost() {
        System.out.println("Enter the ID of the post to delete");
        Long id = scanner.nextLong();
        postController.deletePostById(id);
        System.out.println("Post deleted");
    }

    private void addLabelToPost() {
        System.out.println("Enter id of post to add label: ");
        Long id = scanner.nextLong();

        System.out.println("Enter label's name");
        String name = scanner.nextLine();
        Label label = Label.builder()
                .name(name)
                .build();
        postController.addLabelToPost(id, label);
        System.out.println("The label was added to the post.");
    }


}
