package ru.kpfu.itis.korgutlova_buzukina.classes;

import java.io.*;
import java.net.Socket;

public class Player implements Runnable {
    private Game game;
    private String name;
    private Team team;
    private boolean heading;
    private Socket socket;
    private Thread thread;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Player(Socket socket, Team team) throws IOException {
        this.socket = socket;
        this.team = team;
        this.heading = false;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        this.printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"), true);
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
                if(bufferedReader.ready()) {
                    message = bufferedReader.readLine();
                    if(message.equals(game.getCurrentWord())){
                        if(heading){
                            message += "\n" + this.name + " ведущий не имеет право отвечать! -1 балл команде!";
                            team.degScore();
                        } else {
                            message += "\n" + this.name + " разгадала слово " + message;
                            team.addScore();
                            game.changeWord();
                            System.out.println(game.getCurrentWord());
                        }
                        message += "\n" + "game_score " + team.getName() +  " " + team.getScore();
                    }
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
