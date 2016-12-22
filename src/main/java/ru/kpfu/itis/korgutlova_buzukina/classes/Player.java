package ru.kpfu.itis.korgutlova_buzukina.classes;

import java.io.*;
import java.net.Socket;

public class Player implements Runnable {
    private Game game;
    private String name;
    private Team team;
    private Socket socket;
    private Thread thread;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Player(Socket socket, Team team) throws IOException {
        this.socket = socket;
        this.team = team;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        this.name = bufferedReader.readLine();
    }

    public void start() {
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                if (bufferedReader.ready()) {
                    message = bufferedReader.readLine();
                    //слово угадано
                    if (message.equals(game.getCurrentWord())) {
                        if (this.equals(game.getHeadPlayer())) {
                            message += "\n" + this.name + " ведущий не имеет право отвечать! -1 балл команде!";
                            team.degScore();
                        } else {
                            message += "\n" + this.name + " разгадала слово " + message;
                            team.addScore();
                        }
                        game.changeWord();
                        System.out.println(game.getCurrentWord());
                        message += "\n" + "GAME_SCORE " + team.getName() + " " + team.getScore();
                        game.getHeadPlayer().printWriter.println("GAME_WORD " + game.getCurrentWord());
                    }

                    //смена слова
                    if (message.equals("SKIP")) {
                        message = this.name + " пропустил слово " + game.getCurrentWord();
                        message += "\n" + team.getName() + " потеряла 1 очко";
                        team.degScore();
                        game.changeWord();
                        printWriter.println("GAME_WORD " + game.getCurrentWord());
                    }

                    //смена названия команды
                    if (message.startsWith("NAME_TEAM ")) {
                        String oldName = team.getName();
                        team.setName(message.substring(9));
                        message = "NAME_TEAM " + oldName + " " + team.getName();
                        message += "\n" + this.name + " изменил назване команды на " + team.getName();
                        break;
                    }

                    //Всем рассылаются сообщения
                    for (Player player : game.getPlayerList()) {
                        player.getPrintWriter().println(this.name + " : " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
