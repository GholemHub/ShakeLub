package com.example.shakelab;

import androidx.recyclerview.widget.RecyclerView;

public class NoteIngredient {

    private String nameOfIngredient = "";
    private String countOfIngredient;

    public NoteIngredient(String nameOfIngredient) {
        this.nameOfIngredient = nameOfIngredient;
    }
    public NoteIngredient(String nameOfIngredient, String countOfIngredient) {
        this.nameOfIngredient = nameOfIngredient;
        this.countOfIngredient = countOfIngredient;
    }

    public void changeTex1(String text){
        countOfIngredient = text;
    }

    public NoteIngredient() {

    }

    public String getCountOfIngredient() {
        return countOfIngredient;
    }

    public void setCountOfIngredient(String countOfIngredient) {
        this.countOfIngredient = countOfIngredient;
    }

    public String getNameOfIngredient() {
        return nameOfIngredient;
    }

    public void setNameOfIngredient(String nameOfIngredient) {
        this.nameOfIngredient = nameOfIngredient;
    }
}
