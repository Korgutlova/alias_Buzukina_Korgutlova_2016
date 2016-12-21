package ru.kpfu.itis.korgutlova_buzukina.classes;

public class Team {
    private String name;
    private int score;

    public Team(String name) {
        this.name = name;
    }

    public void addScore(int score){
        this.score += score;
    }

    private void degScore(int score){
        this.score -= score;
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
}
