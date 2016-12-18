package ru.kpfu.itis.korgutlova_buzukina.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable {
    private Game game;
    private String name;
    private Team team;
    private Socket socket;
    private Thread thread;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Player(Game game, Socket socket) throws IOException {
        this.game = game;
        this.socket = socket;
        this.thread = new Thread(this);
        thread.start();
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        //this.name = bufferedReader.readLine();
    }

    @Override
    public void run() {
        try {
            String message = bufferedReader.readLine();
            for (Player player: game.getPlayerList()) {
                player.getPrintWriter().println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
