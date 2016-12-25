package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RulesController {
    private Stage stage;
    private Scene sceneMenu;

    public void clickReturnMenu(MouseEvent mouseEvent) {
        stage.setScene(sceneMenu);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }
}
