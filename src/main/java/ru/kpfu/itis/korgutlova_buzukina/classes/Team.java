package ru.kpfu.itis.korgutlova_buzukina.classes;

public class Team {
    private String name;
    private int score;
    private String players;

    public Team(String name) {
        this.name = name;
        this.score = 0;
        this.players = "";
    }

    public void addScore(){
        this.score += 1;
    }

    public void degScore(){
        this.score -= 1;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayers() {
        return players;
    }

    public void addPlayer(String player) {
        this.players += "%" + player;
    }
}
