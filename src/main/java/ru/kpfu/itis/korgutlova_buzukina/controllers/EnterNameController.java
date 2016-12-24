package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class EnterNameController implements Initializable {

    public TextField name;
    private Scene sceneMenu;
    private static Stage stage;
    private Scene sceneEnterName;

    public static void setStage(Stage stage) {
        EnterNameController.stage = stage;
    }

    public void setSceneEnterName(Scene sceneEnterName) {
        this.sceneEnterName = sceneEnterName;
    }


    public void readName(MouseEvent mouseEvent) throws IOException, InterruptedException {
        int port = 3456;
        String host = "localhost";
        Socket s = new Socket(host, port);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"), true);
        printWriter.println(name.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game.fxml"));
        AnchorPane root = loader.load();
        GameController gameController = loader.getController();
        gameController.setStage(stage);
        Scene sceneGame = new Scene(root);
        gameController.setSceneGame(sceneGame);
        gameController.setIO(bufferedReader, printWriter);
        gameController.setSceneMenu(sceneMenu);
        stage.setScene(sceneGame);
        gameController.connectGame();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }
}