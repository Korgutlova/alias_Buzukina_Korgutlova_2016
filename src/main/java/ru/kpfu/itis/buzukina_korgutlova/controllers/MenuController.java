package main.java.ru.kpfu.itis.buzukina_korgutlova.controllers;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by taa on 18.12.16.
 */
public class MenuController {
    private static Stage stage;

    public void clickOnExitButton(MouseEvent mouseEvent) {
        stage.close();
    }
}
