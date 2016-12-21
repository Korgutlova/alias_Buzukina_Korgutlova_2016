package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuController {
    private static Stage stage;

    public void setStage(Stage stage) {
        MenuController.stage = stage;
    }

    public void clickOnExitButton(MouseEvent mouseEvent) {
        stage.close();
    }
}
