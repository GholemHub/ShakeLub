package com.example.shakelab;

public class Note {
    private String shakeName;
    private String ingredients;
    private int layers;

    public Note(){}

    public Note(String shakeName, String ingredients,int layers){
        this.shakeName = shakeName;
        this.ingredients = ingredients;
        this.layers = layers;
    }

    public String getShakeName() {
        return shakeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getLayers() {
        return layers;
    }
}
