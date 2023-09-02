package ru.richie.view;

import lombok.RequiredArgsConstructor;
import ru.richie.controller.LabelController;
import ru.richie.model.Label;

import java.util.List;
import java.util.Scanner;
@RequiredArgsConstructor
public class LabelView {
    private final LabelController labelController;
    private final Scanner scanner;

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("1. Add label");
            System.out.println("2. Get lsbel by Id");
            System.out.println("3. Get all labels");
            System.out.println("4. Update label");
            System.out.println("5. Delete label");
            System.out.println("0. Cancel");
            System.out.println("Select an option");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addLabel();
                    break;
                case 2:
                    getLabelById();
                    break;
                case 3:
                    getAllLabels();
                    break;
                case 4:
                    updateLabel();
                    break;
                case 5:
                    deleteLabel();
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

    private void addLabel() {
        System.out.println("Enter name");
        String name = scanner.nextLine();
        labelController.addLabel(name);
        System.out.println("Label created: "+name);
    }

    private void getLabelById() {
        System.out.println("Enter label's id: ");
        Long id = scanner.nextLong();
        Label label = labelController.getLabelById(id);
        System.out.println("Labels: " + label.toString());
    }

    private void getAllLabels() {
        List<Label> labels= labelController.getAllLabel();
        for (Label label:
             labels) {
            System.out.println(label.toString());
        }
    }

    private void updateLabel() {
        System.out.println("Enter label's id: ");
        Long id = scanner.nextLong();
        Label oldLabel = labelController.getLabelById(id);
        System.out.println("Old data: " + oldLabel.toString());
        System.out.println("Enter new name: ");
        String newName = scanner.nextLine();

        labelController.updateLabel(id, newName);
    }

    private void deleteLabel() {
        System.out.println("Enter the ID of the label to delete");
        Long id = scanner.nextLong();
        labelController.deleteLabelById(id);
        System.out.println("Label deleted");
    }


}
