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
                    Player headPlayer = game.getPlayerList().get(game.getHeadPlayer());
                    //слово угадано
                    if (message.equals(game.getCurrentWord())) {
                        if (this.equals(headPlayer)) {
                            message = this.name + " : " + message
                                    + "\n" + this.name + " ведущий не имеет право отвечать!"
                                    + "\n" + team.getName() + " потеряла 1 очко";
                            team.degScore();
                        } else {
                            if (!this.team.equals(headPlayer.team)) {
                                message = this.name + " : " + message + "\n"
                                        + this.name + " игрок из другой команды угадал слово " + message
                                        + "\n" + team.getName() + " потеряла 1 очко";
                                team.degScore();
                            } else {
                                message = this.name + " : " + message
                                        + "\n" + this.name + " разгадала слово " + message
                                        + "\n" + team.getName() + " заработала 1 очко";
                                team.addScore();
                            }
                        }
                        game.changeWord();
                        message += "\n" + "TEAM_SCORE " + team.getName() + " " + team.getScore();
                        headPlayer.getPrintWriter().println("GAME_WORD " + game.getCurrentWord());

                    } else if (message.equals("GAME_SKIP")) {
                        message = this.name + " пропустил слово " + game.getCurrentWord();
                        message += "\n" + team.getName() + " потеряла 1 очко";
                        team.degScore();
                        message += "\n" + "TEAM_SCORE " + team.getName() + " " + team.getScore();
                        game.changeWord();
                        printWriter.println("GAME_WORD " + game.getCurrentWord());

                    } else if (message.equals("GAME_HEADING")) {
                        Player player = game.getPlayerList().get(game.getNewHeadPlayer());
                        game.changeWord();
                        player.getPrintWriter().println("GAME_HEADING");
                        player.getPrintWriter().println("GAME_WORD " + game.getCurrentWord());
                        message = "Ведущий в этом раунде " + player.getName();

                    } else {
                        if (!message.equals("SUCCESS")) {
                            message = this.name + " : " + message;
                        }
                    }

                    for (Player player : game.getPlayerList()) {
                        player.getPrintWriter().println(message);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }
}
