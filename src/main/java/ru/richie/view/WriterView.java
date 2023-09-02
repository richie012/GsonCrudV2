package ru.richie.view;

import lombok.RequiredArgsConstructor;
import ru.richie.controller.WriterController;
import ru.richie.model.Label;
import ru.richie.model.Post;
import ru.richie.model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class WriterView {
    private final WriterController writerController;
    private final Scanner scanner;

    public void run(){
        boolean running = true;
        while (running){
            System.out.println("1. Add writer");
            System.out.println("2. Get writer by Id");
            System.out.println("3. Get all writers");
            System.out.println("4. Update writer");
            System.out.println("5. Delete writer");
            System.out.println("6. Add post to writer");
            System.out.println("0. Cancel");
            System.out.println("Select an option");
            int option = scanner.nextInt();
            switch (option){
                case 1:
                    addWriter();
                    break;
                case 2:
                    getWriter();
                    break;
                case 3:
                    getAllWriters();
                    break;
                case 4:
                    updateWriter();
                    break;
                case 5:
                    deleteWriter();
                    break;
                case 6:
                    addPostToWriter();
                    break;
                case 0:
                    running=false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }




    private void addWriter() {
        System.out.println("Enter first name");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name");
        String lastName = scanner.nextLine();
        List<Post> posts = new ArrayList<>();

        writerController.addWriter(firstName, lastName, posts);
        System.out.println("Writer Created: " + firstName + " " + lastName);
    }

    private void getWriter() {
        System.out.println("Enter writer's id: ");
        Long id = scanner.nextLong();
        Writer writer = writerController.getWriterById(id);
        System.out.println("Writer: " + writer.toString());
    }
    private void getAllWriters() {
        List<Writer> writers = writerController.getAllWriters();
        for (Writer writer:
             writers) {
            System.out.println(writer.toString());
        }
    }

    private void updateWriter() {
        System.out.println("Enter writer id: ");
        Long id = scanner.nextLong();
        Writer oldWriter= writerController.getWriterById(id);

        System.out.println("Old data "+ oldWriter.toString());
        System.out.println("Enter new first name: ");

        String newFirstName = scanner.nextLine();
        System.out.println("Enter new last name: ");
        String newLastName = scanner.nextLine();

        writerController.updateWriter(id, newFirstName, newLastName);

        System.out.println("Writer updated.");
    }

    private void deleteWriter() {
        System.out.println("Enter the ID of the writer to delete");
        Long id = scanner.nextLong();
        writerController.deleteWriterById(id);
        System.out.println("Writer deleted.");
    }

    private void addPostToWriter() {
        System.out.println("Enter id of writer to add post: ");
        Long id = scanner.nextLong();

        System.out.println("Enter content post");
        String content = scanner.nextLine();

        List<Label> labels = new ArrayList<>();

        Post newPost = Post.builder()
                .content(content)
                .labels(labels)
                .build();

        writerController.addPostToWriter(id, newPost);
        System.out.println("The post added to the writer.");
    }
}
