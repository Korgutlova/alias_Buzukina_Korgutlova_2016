package ru.kpfu.itis.korgutlova_buzukina.server;

import ru.kpfu.itis.korgutlova_buzukina.classes.Game;
import ru.kpfu.itis.korgutlova_buzukina.classes.Player;
import ru.kpfu.itis.korgutlova_buzukina.classes.Team;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Game> list;
    private final int PORT = 3456;
    private final int NUMBER = 2;

    public Server() {
        init();
    }

    private void init() {
        list = new ArrayList<>();
    }

    public void go() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            ArrayList<Player> players = new ArrayList<>();
            Team teamRed = new Team("Red");
            Team teamBlue = new Team("Blue");
            while(players.size() != NUMBER){
                Socket socket = ss.accept();
                players.add(new Player(socket, teamRed));
                System.out.println("Client connected");
                socket = ss.accept();
                players.add(new Player(socket, teamBlue));
                System.out.println("Client connected");
            }
            Game game = new Game(players, teamRed, teamBlue);
            list.add(game);
        }
    }

    public static void main(String[] args) throws IOException {
        (new Server()).go();
    }
}