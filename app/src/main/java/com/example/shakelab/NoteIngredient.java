package com.example.shakelab;

public class NoteIngredient {

    private String nameOfIngredient = "";
    private String countOfIngredient;
    public String btnName = "";

    public String getPercentOfIngredient() {
        return percentOfIngredient;
    }

    public void setPercentOfIngredient(String percentOfIngredient) {
        this.percentOfIngredient = percentOfIngredient;
    }

    private String percentOfIngredient = "";

    public void setText(){
       //setNameBtn(String str);
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

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
