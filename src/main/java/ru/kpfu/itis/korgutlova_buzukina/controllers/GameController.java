package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable{
    public TextField message;
    public TextArea chat;
    public Button skip;
    public Label labelWord;
    public Label word;
    private Stage stage;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean heading;

    public void sendMessage(MouseEvent mouseEvent) throws IOException {
        printWriter.println(message.getText());
        System.out.println(message.getText());
        message.setText("");
        while (bufferedReader.ready()){
            String line = bufferedReader.readLine();
            if(line.startsWith("GAME_WORD ") && heading){
                word.setText(line.substring(10));
            }
            System.out.println(line);
            chat.setText(chat.getText() + "\n" + line);
        }
        System.out.println(chat.getText());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setIO(BufferedReader bufferedReader, PrintWriter printWriter) {
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        printWriter.println("Natasha");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        skip.setVisible(true);
        labelWord.setVisible(true);
        word.setVisible(true);
        heading = true;
    }

    public void skipWord(MouseEvent mouseEvent) {
        printWriter.println("SKIP");
    }
}
