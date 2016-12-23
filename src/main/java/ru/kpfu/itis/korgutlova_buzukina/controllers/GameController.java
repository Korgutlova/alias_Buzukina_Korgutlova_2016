package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public TextArea message;
    public TextArea chat;
    public Button skip;
    public Label labelWord;
    public Label word;
    public Button send;
    public Label timeLabel;
    private Stage stage;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean heading;
    private Scene sceneGame;

    public void sendMessage(MouseEvent mouseEvent) throws IOException {
        printWriter.println(message.getText());
        message.setText("");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setWord(String newWord) {
        word.setText(newWord);
    }

    public void setIO(BufferedReader bufferedReader, PrintWriter printWriter) {
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chat.setEditable(false);
        chat.setScrollTop(Double.MAX_VALUE);
        chat.setText("Ожидайте подключения других игроков");
        send.setVisible(false);
        message.setVisible(false);
        heading(false);
    }

    public void connectGame() throws IOException {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (!bufferedReader.ready()) ;
                Platform.runLater(() -> {
                            try {
                                chat.setText(bufferedReader.readLine());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            message.setVisible(true);
                            send.setVisible(true);
                            startGame();
                        }
                );
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void startGame() {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int round = 1;
                int number = 4;
                long roundTime = 15000;
                changeHeading();
                while (round < 2) {
                    int i = 0;
                    while (i < number) {
                        long time = System.currentTimeMillis() + roundTime;
                        Timeline timeline = timerAnimated(60);
                        while (timeline.getStatus().equals(Animation.Status.RUNNING)) {
                            while (bufferedReader.ready()) {
                                if (timeline.getStatus().equals(Animation.Status.STOPPED)) {
                                    break;
                                }
                                String line = bufferedReader.readLine();
                                if (line.startsWith("GAME_WORD ") && heading) {
                                    Platform.runLater(() -> word.setText(line.substring(10)));
                                } else {
                                    Platform.runLater(() -> chat(line));
                                }
                            }
                        }
                        int finalI = i;
                        Platform.runLater(() -> chat.appendText("\n" + finalI + " раунд закончен"));
                        i++;
                        if (heading) {
                            printWriter.println("GAME_HEADING");
                            Platform.runLater(() -> heading(false));
                        }
                        changeHeading();
                    }
                    round++;
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();


    }

    private void changeHeading() throws IOException {
        while(!bufferedReader.ready()) ;
        String line = bufferedReader.readLine();
        if (line.equals("GAME_HEADING")) {
            Platform.runLater(() -> heading(true));
        } else {
            Platform.runLater(() -> chat(line));
        }
    }
    private void chat(String line) {
        chat.appendText("\n" + line);
    }

    public Timeline timerAnimated(int timeInSeconds) {
        int[] time = {timeInSeconds};
        Platform.runLater(() -> timeLabel.setText((time[0] / 60) + ":" + ((time[0] % 60) < 10 ? "0" : "") + (time[0] % 60)));
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            time[0] -= 1;
                            String timeValue = (time[0] / 60) + ":" + ((time[0] % 60) < 10 ? "0" : "") + (time[0] % 60);
                            Platform.runLater(() -> timeLabel.setText(timeValue));
                        }
                )
        );
        timeline.setCycleCount(timeInSeconds);
        timeline.play();
        return timeline;
    }

    public void skipWord(MouseEvent mouseEvent) {
        printWriter.println("GAME_SKIP");
    }

    public void setSceneGame(Scene sceneGame) {
        this.sceneGame = sceneGame;
    }

    private void heading(boolean bool) {
        this.heading = bool;
        skip.setVisible(bool);
        labelWord.setVisible(bool);
        word.setVisible(bool);
    }
}
