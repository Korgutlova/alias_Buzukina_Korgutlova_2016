package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.korgutlova_buzukina.classes.Player;
import java.io.IOException;


/**
 * Created by taa on 23.12.16.
 */
public class EnterNameController {

    public static void setStage(Stage stage) {
        EnterNameController.stage = stage;
    }

    private static Stage stage;
    private TextField name;

    public void setSceneEnterName(Scene sceneEnterName) {
        this.sceneEnterName = sceneEnterName;
    }

    private Scene sceneEnterName;
    private Player player;


    public void readName(MouseEvent mouseEvent) throws IOException {
        String name = this.name.getText();
        player.setName(name);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game.fxml"));
        AnchorPane root = loader.load();
        GameController gameController = loader.getController();
        gameController.setStage(stage);
        Scene sceneGame = new Scene(root, 500, 400);
        gameController.setSceneGame(sceneGame);
        stage.setScene(sceneGame);
    }
}
