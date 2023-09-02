package ru.richie;

import ru.richie.controller.LabelController;
import ru.richie.controller.PostController;
import ru.richie.controller.WriterController;
import ru.richie.enums.Status;
import ru.richie.model.Label;
import ru.richie.model.Post;
import ru.richie.model.Writer;
import ru.richie.repositories.LabelRepo;
import ru.richie.repositories.PostRepo;
import ru.richie.repositories.WriterRepo;
import ru.richie.repositories.json.LabelRepositoryImp;
import ru.richie.repositories.json.PostRepositoryImp;
import ru.richie.repositories.json.WriterRepositoryImp;
import ru.richie.view.LabelView;
import ru.richie.view.PostView;
import ru.richie.view.WriterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Label");
        System.out.println("2. Post");
        System.out.println("3. Writer");
        System.out.println("Select option");

        int option = scanner.nextInt();

        switch (option){
            case 1:
                LabelRepo labelRepo = new LabelRepositoryImp();
                LabelController labelController = new LabelController(labelRepo);
                LabelView labelView = new LabelView(labelController, scanner);
                labelView.run();
                break;
            case 2:
                PostRepo postRepo = new PostRepositoryImp();
                PostController postController=new PostController(postRepo);
                PostView postView = new PostView(postController, scanner);
                postView.run();
                break;
            case 3:
                WriterRepo writerRepo = new WriterRepositoryImp();
                WriterController writerController = new WriterController(writerRepo);
                WriterView writerView = new WriterView(writerController, scanner);
                writerView.run();

                break;
            default:
                System.out.println("Invalid option");
        }



    }
}
