package ru.kpfu.itis.korgutlova_buzukina.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    public TextArea team;
    public Label labelWord;
    public Label word;
    public Button send;
    public Label timeLabel;
    public Label redTeamScore;
    public Label blueTeamScore;
    private Stage stage;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean heading;
    private Scene sceneGame;
    private Scene sceneMenu;

    public void sendMessage(MouseEvent mouseEvent) throws IOException {
        printWriter.println(message.getText());
        message.clear();
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
        team.setEditable(false);
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
                                String [] line = bufferedReader.readLine().split("%");
                                String color = (line[1].equals("Red") ? "darksalmon" : "cornflowerblue");
                                chat.appendText("\nYou in " + line[1] + " team!");
                                send.setStyle("-fx-background-color: " + color);
                                int i = 2;
                                while(i < line.length){
                                    team.appendText(line[i] + "\n");
                                    i++;
                                }
                                team.setStyle("-fx-text-fill: " + color);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            message.setVisible(true);
                            send.setVisible(true);
                            try {
                                startGame();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void startGame() throws IOException {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int round = 1;
                int j = 0;
                int number = 3;
                int roundTime = 5;
                changeHeading();
                while (j < round) {
                    int i = 1;
                    while (i <= number) {
                        Timeline timeline = timerAnimated(roundTime);
                        while (timeline.getStatus().equals(Animation.Status.RUNNING)) {
                            while (bufferedReader.ready()) {
                                if (timeline.getStatus().equals(Animation.Status.STOPPED)) {
                                    break;
                                }
                                addMessageToChat(bufferedReader.readLine());
                            }
                        }
                        send.setDisable(true);
                        skip.setDisable(true);
                        Thread.sleep(1000);
                        int finalI1 = i;
                        Platform.runLater(() -> chat.appendText("\n" + finalI1 + " раунд закончен"));
                        if (heading) {
                            printWriter.println("GAME_HEADING");
                            Platform.runLater(() -> heading(false));
                        }
                        changeHeading();
                        i += 1;
                        Thread.sleep(3000);
                        send.setDisable(false);
                        skip.setDisable(false);
                    }
                    j++;
                }
                System.out.println("ufff");
                endGame();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

    private void endGame() throws IOException {
        int redScore = Integer.parseInt(redTeamScore.getText());
        int blueScore = Integer.parseInt(blueTeamScore.getText());
        String score = redScore + " : " + blueScore;
        String result;
        if(redScore > blueScore){
            result = "Red team win!!!";
        } else if(redScore == blueScore){
            result = "Draw!!!";
        } else {
            result = "Blue team win!!!";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/endGame.fxml"));
        AnchorPane root = loader.load();
        EndGameController endGameController = loader.getController();
        EndGameController.setStage(stage);
        Scene sceneEndGame = new Scene(root);
        endGameController.setSceneMenu(sceneMenu);
        endGameController.setSceneEndGame(sceneEndGame);
        endGameController.initLabel(score, result);
        stage.setScene(sceneEndGame);
    }

    public void addMessageToChat(String line){
        System.out.println("kek");
        System.out.println(line);
        if (line.startsWith("GAME_WORD ") && heading) {
            Platform.runLater(() -> word.setText(line.substring(10)));
        } else if (line.startsWith("TEAM_SCORE ")) {
            String[] array = line.split(" ");
            if (array[1].equals("Red")) {
                Platform.runLater(() -> redTeamScore.setText(array[2]));
            } else {
                Platform.runLater(() -> blueTeamScore.setText(array[2]));
            }
        } else {
            Platform.runLater(() -> chat(line));
        }
    }

    private void changeHeading() throws IOException {
        boolean flag = false;
        while (!flag) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                System.out.println(line);
                System.out.println("main");
                if (line.equals("GAME_HEADING")) {
                    Platform.runLater(() -> heading(true));
                    printWriter.println("SUCCESS");
                } else if (line.equals("SUCCESS")) {
                    flag = true;
                    break;
                } else{
                    addMessageToChat(line);
                }
            }
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

    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }
}
