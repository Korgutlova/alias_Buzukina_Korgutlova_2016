package ru.kpfu.itis.korgutlova_buzukina.classes;

public class Team {
    private String name;
    private int score;

    public Team(String name) {
        this.name = name;
        this.score = 0;
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
}
