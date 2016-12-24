package ru.kpfu.itis.korgutlova_buzukina.controllers;


import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    private static Stage stage;
    public Label result;
    public Label score;
    private Scene sceneMenu;
    private Scene sceneEndGame;

    public static void setStage(Stage stage) {
        EndGameController.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }

    public void setSceneEndGame(Scene sceneEndGame) {
        this.sceneEndGame = sceneEndGame;
    }

    public void initLabel(String score, String result) {
        this.score.setText(score);
        this.result.setText(result);
    }

    public void returnMenu(MouseEvent mouseEvent) {
        stage.setScene(sceneMenu);
    }
}
