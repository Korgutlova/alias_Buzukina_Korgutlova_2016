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
            while (players.size() != NUMBER) {
                addPlayer(ss, players, teamRed);
                addPlayer(ss, players, teamBlue);
            }
            Game game = new Game(players, teamRed, teamBlue);
            list.add(game);
        }
    }

    public static void main(String[] args) throws IOException {
        (new Server()).go();
    }

    public void addPlayer(ServerSocket ss, List<Player> players, Team team) throws IOException {
        Socket socket = ss.accept();
        Player playerNew = new Player(socket, team);
        players.add(playerNew);
        while (playerNew.getName() == null) ;
        team.addPlayer(playerNew.getName());
        System.out.println("Client connected");
    }
}