package ru.kpfu.itis.korgutlova_buzukina.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GameDictionary {
    private ArrayList<String> words = new ArrayList<>();
    public GameDictionary(){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("files/dictionary.txt")));
        try {
            while(reader.ready()){
                words.add(reader.readLine().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWord(){
        int index = new Random().nextInt(words.size());
        System.out.println(index);
        String word = words.get(index);;
        words.remove(index);
        System.out.println(words.size());
        return word;
    }
}
