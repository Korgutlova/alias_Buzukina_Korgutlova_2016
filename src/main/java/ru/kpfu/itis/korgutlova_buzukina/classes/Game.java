package ru.kpfu.itis.korgutlova_buzukina.classes;

import java.util.List;

public class Game implements Runnable {
    private List<Player> playerList;
    private Thread thread;
    private int rounds;
    private int currentRound;
    private Team teamRed;
    private Team teamBlue;

    public Game(List<Player> playerList) {
        this.playerList = playerList;
        this.thread = new Thread(this);
        thread.start();
        System.out.println("Game create");
    }

    @Override
    public void run() {
        while (playerList.size() != 6) ;
        System.out.println("Game started");

    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
