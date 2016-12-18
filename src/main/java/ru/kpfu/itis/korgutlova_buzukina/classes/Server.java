package ru.kpfu.itis.korgutlova_buzukina.classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Game> list;
    private final int PORT = 3456;

    public Server() {
        init();
    }

    private void init() {
        list = new ArrayList<>();
    }

    public void go() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            List<Player> players = new ArrayList<>();
            Game game = new Game(players);
            list.add(game);
            while(players.size() != 6){
                Socket socket = ss.accept();
                players.add(new Player(game, socket));
                System.out.println("Client connected");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        (new Server()).go();
    }
}