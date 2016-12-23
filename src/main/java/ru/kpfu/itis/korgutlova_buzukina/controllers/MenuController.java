package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MenuController implements Initializable{
    private static Stage stage;


    public void clickOnPlayButton(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/enterName.fxml"));
        AnchorPane root = loader.load();
        EnterNameController enterNameController = loader.getController();
        EnterNameController.setStage(stage);
        Scene sceneEnterName = new Scene(root);
        enterNameController.setSceneEnterName(sceneEnterName);
        stage.setScene(sceneEnterName);
    }

    public void setStage(Stage stage) {
        MenuController.stage = stage;
    }

    public void clickOnExitButton(MouseEvent mouseEvent) {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}