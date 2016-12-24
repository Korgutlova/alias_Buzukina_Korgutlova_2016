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
    public TextArea team;
    public Label labelWord;
    public Label word;
    public Button send;
    public Label timeLabel;
    public Label redTeamScore;
    public Label blueTeamScore;
    public Label resultLabel;
    public Button endButton;
    private Stage stage;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean heading;
    private Scene sceneGame;
    private Scene sceneMenu;
    private final static int NUMBER = 6;
    private final static int ROUND = 1;
    private final static int ROUND_TIME = 180;

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
        endButton.setVisible(false);
        resultLabel.setVisible(false);
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
                                String[] line = bufferedReader.readLine().split("%");
                                String color = (line[1].equals("Red") ? "darksalmon" : "cornflowerblue");
                                chat.appendText("\nYou in " + line[1] + " team!");
                                send.setStyle("-fx-background-color: " + color);
                                int i = 2;
                                while (i < line.length) {
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

    private void startGame() throws IOException {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int j = 1;
                int i = 1;
                changeHeading();
                while (j <= ROUND) {
                    i = 1;
                    while (i <= NUMBER) {
                        Timeline timeline = timerAnimated(ROUND_TIME);
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
                        Thread.sleep(2000);
                        send.setDisable(false);
                        skip.setDisable(false);
                    }
                    j++;
                }
                printWriter.println("GAME FINISHED");
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
        Platform.runLater(() -> {
            if (redScore > blueScore) {
                resultLabel.setText("Red team win!!!");
                resultLabel.setStyle("-fx-text-fill: #b43a28");
            } else if (redScore == blueScore) {
                resultLabel.setText("Draw!!!");
            } else {
                resultLabel.setText("Blue team win!!!");
                resultLabel.setStyle("-fx-text-fill: #6e94b4");
            }
        });
        send.setDisable(true);
        skip.setDisable(true);
        chat.setVisible(false);
        endButton.setVisible(true);
        resultLabel.setVisible(true);
    }

    private void addMessageToChat(String line) {
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
            label:
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                switch (line) {
                    case "GAME_HEADING":
                        Platform.runLater(() -> heading(true));
                        printWriter.println("SUCCESS");
                        break;
                    case "SUCCESS":
                        flag = true;
                        break label;
                    default:
                        addMessageToChat(line);
                        break;
                }
            }
        }
    }

    private void chat(String line) {
        chat.appendText("\n" + line);
    }

    private Timeline timerAnimated(int timeInSeconds) {
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

    public void returnMenu(MouseEvent mouseEvent) {
        stage.setScene(sceneMenu);
    }
}
