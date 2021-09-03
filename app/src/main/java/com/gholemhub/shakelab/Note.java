package com.gholemhub.shakelab;

import java.util.List;

public class Note {
    private String shakeName;
    private int countOfLayers;
    private String shakeImage;
    private String shakeIngredientsString;
    private String shakeIngredientsString2;
    private String shakeIngredientsString3;
    private String listPercentOfIngredients;


    public String getListPercentOfIngredients() {
        return listPercentOfIngredients;
    }

    public void setListPercentOfIngredients(String listPercentOfIngredients) {
        this.listPercentOfIngredients = listPercentOfIngredients;
    }



    public Note(){}

    public Note(String shakeName,int layers, String shakeImage){
        this.shakeName = shakeName;
        this.shakeImage = shakeImage;
        this.countOfLayers = layers;
    }

    public Note(String shakeName,int layers, String shakeImage, String shakeIngredientsString){
        this.shakeName = shakeName;
        this.shakeImage = shakeImage;
        this.countOfLayers = layers;
        this.shakeIngredientsString = shakeIngredientsString;
        this.shakeIngredientsString2 = shakeIngredientsString;
    }
    public Note(String shakeName,int layers, String shakeImage, String shakeIngredientsString, String listPercentOfIngredients){
        this.shakeName = shakeName;
        this.shakeImage = shakeImage;
        this.countOfLayers = layers;
        this.shakeIngredientsString = shakeIngredientsString;//INFO WITHOUT '\N'
        this.shakeIngredientsString2 = shakeIngredientsString;//INFO WITH '\N'
        this.listPercentOfIngredients = listPercentOfIngredients;
    }

    public String getShakeImage() {
        return shakeImage;
    }

    public String getShakeIngredientsString() {
        return shakeIngredientsString;
    }//INFO WITHOUT '\N'

    public String getShakeIngredientsString3() {
        return shakeIngredientsString3;
    }//INFO WITHOUT '\N'

    public void setShakeIngredientsString2(String shakeIngredientsString2) {
        this.shakeIngredientsString2 = shakeIngredientsString2;
    }

    public String getShakeIngredientsString2() {
        return shakeIngredientsString2;
    }//INFO WITH '\N'

    public void setShakeIngredientsString(String shakeIngredientsString) {
        this.shakeIngredientsString = shakeIngredientsString;
    }

    public String getShakeName()
    {
        return shakeName;
    }


    public int getCountOfLayers() {
        return countOfLayers;
    }

    public void setShakeName(String shakeName){
        this.shakeName = shakeName;
    }

    public void setShakeImage(String shakeImage){
        this.shakeImage = shakeImage;
    }

    public void setCountOfLayers(int countOfLayers){
        this.countOfLayers = countOfLayers;
    }

}
