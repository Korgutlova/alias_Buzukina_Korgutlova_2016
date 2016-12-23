package ru.kpfu.itis.korgutlova_buzukina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.kpfu.itis.korgutlova_buzukina.controllers.MenuController;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        AnchorPane root = loader.load();
        MenuController menuController = loader.getController();
        menuController.setStage(primaryStage);
        Scene scene = new Scene(root);
        initStage(primaryStage, scene);
    }

    private void initStage(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Alias");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
