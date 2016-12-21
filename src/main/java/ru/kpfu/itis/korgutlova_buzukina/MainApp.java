package ru.kpfu.itis.korgutlova_buzukina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.korgutlova_buzukina.controllers.GameController;
import ru.kpfu.itis.korgutlova_buzukina.controllers.MenuController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/menu.fxml"));
//        AnchorPane root = loader.load();
//        MenuController menuController = loader.getController();
//        menuController.setStage(primaryStage);
//        Scene scene = new Scene(root);
//        initStage(primaryStage, scene);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game.fxml"));
        AnchorPane root = loader.load();
        GameController gameController = loader.getController();
        gameController.setStage(primaryStage);
        Scene scene = new Scene(root);
        initStage(primaryStage, scene);

        Scanner scanner = new Scanner(System.in);
        int port = 3456;
        String host = "localhost";
        Socket s = new Socket(host, port);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
        gameController.setIO(bufferedReader, printWriter);
    }

    private void initStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Alias");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
