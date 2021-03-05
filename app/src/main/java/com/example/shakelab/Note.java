package com.example.shakelab;

public class Note {
    private String shakeName;
    private int countOfLayers;

    public Note(){}

    public Note(String shakeName, String ingredients,int layers){
        this.shakeName = shakeName;

        this.countOfLayers = layers;
    }

    public String getShakeName() {
        return shakeName;
    }



    public int getCountOfLayers() {
        return countOfLayers;
    }
}
