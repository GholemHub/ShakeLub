package com.example.shakelab;

import androidx.recyclerview.widget.RecyclerView;

public class NoteIngredient {

    private String nameOfIngredient = "";

    public NoteIngredient(String nameOfIngredient) {
        this.nameOfIngredient = nameOfIngredient;
    }

    public NoteIngredient() {

    }

    public String getNameOfIngredient() {
        return nameOfIngredient;
    }

    public void setNameOfIngredient(String nameOfIngredient) {
        this.nameOfIngredient = nameOfIngredient;
    }
}
