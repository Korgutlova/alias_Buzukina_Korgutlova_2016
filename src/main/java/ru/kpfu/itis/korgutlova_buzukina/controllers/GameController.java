package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GameController {
    public TextField message;
    public TextArea chat;
    private Stage stage;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    public void sendMessage(MouseEvent mouseEvent) throws IOException {
        printWriter.println(message.getText());
        message.setText("");
        while (bufferedReader.ready()){
            chat.setText(chat.getText() + "\n" + bufferedReader.readLine());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setIO(BufferedReader bufferedReader, PrintWriter printWriter) {
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
    }
}
