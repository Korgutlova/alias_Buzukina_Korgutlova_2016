package ru.kpfu.itis.korgutlova_buzukina.classes;

import ru.kpfu.itis.korgutlova_buzukina.helpers.GameDictionary;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private ArrayList<Player> playerList;
    private int headPlayer;
    private Thread thread;
    private String currentWord;
    private Team teamOne;
    private Team teamTwo;
    private GameDictionary dictionary;

    public Game(ArrayList<Player> playerList, Team teamOne, Team teamTwo) {
        this.playerList = playerList;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        for (Player player : playerList) {
            player.setGame(this);
            player.getPrintWriter().println("Game started");
            String team = (player.getTeam().equals(teamOne)) ? "Red" : "Blue";
            player.getPrintWriter().println("GAME_TEAM%" + team +  player.getTeam().getPlayers());
            player.start();
        }
        dictionary = new GameDictionary();
        this.currentWord = dictionary.getWord();
        playerList.get(0).getPrintWriter().println("GAME_HEADING");
        playerList.get(0).getPrintWriter().println("GAME_WORD " + currentWord);
        headPlayer = 0;
        for (Player player : playerList) {
            player.getPrintWriter().println("Ведущий в этом раунде " + playerList.get(0).getName());
        }
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

    public GameDictionary getDictionary() {
        return dictionary;
    }

    public void changeWord() {
        this.currentWord = getDictionary().getWord();
    }

    public int getHeadPlayer() {
        return headPlayer;
    }

    public void setHeadPlayer(int headPlayer) {
        this.headPlayer = headPlayer;
    }

    public int getNewHeadPlayer() {
        if (headPlayer != playerList.size() - 1) {
            headPlayer += 1;
            return headPlayer;
        } else {
            headPlayer = 0;
            return headPlayer;
        }
    }
}
