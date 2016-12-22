package ru.kpfu.itis.korgutlova_buzukina.classes;

import java.util.List;

public class Game implements Runnable {
    private List<Player> playerList;
    private Thread thread;
    private int rounds;
    private int currentRound;
    private String currentWord;
    private Team teamOne;
    private Team teamTwo;
    private GameDictionary dictionary;

    public Game(List<Player> playerList, Team teamOne, Team teamTwo) {
        this.playerList = playerList;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        for (Player player : playerList) {
            player.setGame(this);
            player.start();
        }
        dictionary = new GameDictionary();
        this.currentWord = dictionary.getWord();
        this.thread = new Thread(this);
        thread.start();
        System.out.println("Game create");
    }

    @Override
    public void run() {
        System.out.println("Game started");
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }
    public GameDictionary getDictionary(){
        return dictionary;
    }

    public void changeWord(){
        this.currentWord = getDictionary().getWord();
    }
}
