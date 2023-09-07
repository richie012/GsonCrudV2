package ru.richie;

import ru.richie.controller.LabelController;
import ru.richie.controller.PostController;
import ru.richie.controller.WriterController;
import ru.richie.repository.LabelRepo;
import ru.richie.repository.PostRepo;
import ru.richie.repository.WriterRepo;
import ru.richie.repository.gson.LabelRepositoryImp;
import ru.richie.repository.gson.PostRepositoryImp;
import ru.richie.repository.gson.WriterRepositoryImp;
import ru.richie.view.LabelView;
import ru.richie.view.MainView;
import ru.richie.view.PostView;
import ru.richie.view.WriterView;

import java.util.Scanner;

public class AppRunner {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.start();
    }
}
